package cn.edu.xmu.oomall.wheel;

import cn.edu.xmu.oomall.dao.TaskDao;
import cn.edu.xmu.oomall.dto.TaskMessageBody;
import cn.edu.xmu.oomall.sender.ISender;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author xincong yao
 * @date 2020-11-19
 */
@Component
@Slf4j
public class MultiLayerTimeWheel {

	@Autowired
	private TaskDao taskDao;

	@Resource(name = "rocketMQSenderImpl")
	private ISender sender;

	private List<List<TaskMessageBody>> hourDurationTimeWheel;
	private List<List<TaskMessageBody>> minuteDurationTimeWheel;
	private List<List<TaskMessageBody>> secondDurationTimeWheel;

	private int hwPointer;
	private int mwPointer;
	private int swPointer;

	private Object[] hwLock;
	private Object[] mwLock;
	private Object[] swLock;

	private Deque<TaskMessageBody> taskQueue;

	private int hwTicksPerWheel = 24;
	private int mwTicksPerWheel = 60;
	private int swTicksPerWheel = 60;

	private static final int MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;
	private static final int MILLISECONDS_PER_HOUR = 60 * 60 * 1000;
	private static final int MILLISECONDS_PER_MINUTE = 60 * 1000;
	private static final int MILLISECONDS_PER_SECOND = 1000;
	private static final int MILLISECONDS_PER_TASK = 1000;

	@PostConstruct
	public void init() {
		this.hwLock = new Object[hwTicksPerWheel];
		this.mwLock = new Object[mwTicksPerWheel];
		this.swLock = new Object[swTicksPerWheel];
		Arrays.fill(hwLock, new Object());
		Arrays.fill(mwLock, new Object());
		Arrays.fill(swLock, new Object());

		hourDurationTimeWheel = new ArrayList<>();
		minuteDurationTimeWheel = new ArrayList<>();
		secondDurationTimeWheel = new ArrayList<>();
		taskQueue = new ConcurrentLinkedDeque<>();
		hwPointer = 0;
		mwPointer = 0;
		swPointer = 0;
		for (int i = 0; i < hwTicksPerWheel; i++) {
			hourDurationTimeWheel.add(new LinkedList<>());
		}
		for (int i = 0; i < mwTicksPerWheel; i++) {
			minuteDurationTimeWheel.add(new LinkedList<>());
		}
		for (int i = 0; i < swTicksPerWheel; i++) {
			secondDurationTimeWheel.add(new LinkedList<>());
		}

	}

	@Scheduled(fixedRate = MILLISECONDS_PER_DAY)
	public void loadTasksFromDatabase() {
		LocalDateTime now = LocalDateTime.now();
		List<TaskMessageBody> messages = taskDao.getTaskMessageBodies(now, now.plusDays(1));

		for (TaskMessageBody task : messages) {
			fit(task);
		}
	}

	@Scheduled(fixedRate = MILLISECONDS_PER_HOUR, initialDelay = 5000)
	public void hourDurationTask() {
		List<TaskMessageBody> taskList;
		synchronized (hwLock[hwPointer]) {
			taskList = hourDurationTimeWheel.get(hwPointer);
			hourDurationTimeWheel.set(hwPointer, new LinkedList<>());
			hwPointer = next(hwPointer, hwTicksPerWheel);
		}

		for (TaskMessageBody task : taskList) {
			fit(task);
		}
	}

	@Scheduled(fixedRate = MILLISECONDS_PER_MINUTE, initialDelay = 5000)
	public void minuteDurationTask() {
		List<TaskMessageBody> taskList;
		synchronized (mwLock[mwPointer]) {
			taskList = minuteDurationTimeWheel.get(mwPointer);
			minuteDurationTimeWheel.set(mwPointer, new LinkedList<>());
			mwPointer = next(mwPointer, mwTicksPerWheel);
		}

		for (TaskMessageBody task : taskList) {
			fit(task);
		}
	}

	@Scheduled(fixedRate = MILLISECONDS_PER_SECOND, initialDelay = 5000)
	public void secondDurationTask() {
		List<TaskMessageBody> taskList;
		synchronized (swLock[swPointer]) {
			taskList = secondDurationTimeWheel.get(swPointer);
			secondDurationTimeWheel.set(swPointer, new LinkedList<>());
			swPointer = next(swPointer, swTicksPerWheel);
		}

		for (TaskMessageBody task : taskList) {
			taskQueue.offer(task);
		}
	}

	@Scheduled(fixedRate = MILLISECONDS_PER_TASK)
	public void execute() {
		while (!taskQueue.isEmpty()) {
			TaskMessageBody t = taskQueue.poll();
			if (t == null) {
				return;
			}
			sender.sendOneWay(JSON.toJSONString(t), t.getTopic(), t.getTag());
		}
	}

	@Scheduled(fixedDelay = 1000)
	private void debug() {
		for (int i = 0; i < hwTicksPerWheel; i++) {
			if (hourDurationTimeWheel.get(i).size() != 0) {
				log.info("hw: " + i + " " + hourDurationTimeWheel.get(i).size());
			}
		}
		for (int i = 0; i < mwTicksPerWheel; i++) {
			if (minuteDurationTimeWheel.get(i).size() != 0) {
				log.info("mw: " + i + " " + minuteDurationTimeWheel.get(i).size());
			}
		}
		for (int i = 0; i < swTicksPerWheel; i++) {
			if (secondDurationTimeWheel.get(i).size() != 0) {
				log.info("sw: " + i + " " + secondDurationTimeWheel.get(i).size());
			}
		}
	}

	/**
	 *
	 * @param base 轮指针的初始位置
	 * @param n 单位为秒
	 * @param ticks 每轮的标记数
	 * @param duration 标记之间的间隔, 单位为秒
	 * @return
	 */
	private int index(int base, long n, int ticks, int duration) {
		return (int) ((base + n / duration + 1) % ticks);
	}

	private int next(int cur, int mod) {
		return (cur + 1) % mod;
	}

	@Deprecated
	private List<TaskMessageBody> roll(Object[] lock, int pointer,
									   List<List<TaskMessageBody>> wheel,
									   int ticksPerWheel) {
		List<TaskMessageBody> taskList;
		synchronized (lock[pointer]) {
			taskList = wheel.get(pointer);
			wheel.set(pointer, new LinkedList<>());
			// 将轮子属性封装到类里
			// todo
			pointer = next(pointer, ticksPerWheel);
		}
		return taskList;
	}

	private void fit(TaskMessageBody message) {
		long seconds = Duration.between(LocalDateTime.now(), message.getPrepareTime()).getSeconds();
		if (seconds <= 0) {
			taskQueue.add(message);
			return;
		}
		if (seconds <= MILLISECONDS_PER_MINUTE / MILLISECONDS_PER_SECOND) {
			int indexInSecondWheel = index(swPointer, seconds, swTicksPerWheel, 1);
			synchronized (swLock[indexInSecondWheel]) {
				secondDurationTimeWheel.get(indexInSecondWheel).add(message);
			}
			return;
		}
		if (seconds <= MILLISECONDS_PER_HOUR / MILLISECONDS_PER_SECOND) {
			int indexInMinuteWheel = index(mwPointer, seconds, mwTicksPerWheel, MILLISECONDS_PER_MINUTE / MILLISECONDS_PER_SECOND);
			synchronized (mwLock[indexInMinuteWheel]) {
				minuteDurationTimeWheel.get(indexInMinuteWheel).add(message);
			}
			return;
		}

		int indexInHourWheel = index(hwPointer, seconds, hwTicksPerWheel, MILLISECONDS_PER_HOUR / MILLISECONDS_PER_SECOND);
		synchronized (hwLock[indexInHourWheel]) {
			hourDurationTimeWheel.get(indexInHourWheel).add(message);
		}
	}

}