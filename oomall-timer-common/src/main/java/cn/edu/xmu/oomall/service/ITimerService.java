package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.dto.TaskMessageBody;

/**
 * @author xincong yao
 * @date 2020-11-21
 */
public interface ITimerService {

	Boolean newTimerTask(TaskMessageBody message);

}
