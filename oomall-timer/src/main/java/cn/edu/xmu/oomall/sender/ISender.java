package cn.edu.xmu.oomall.sender;

/**
 * @author xincong yao
 * @date 2020-11-21
 */
public interface ISender {

	void sendOneWay(Object o, String topic, String tag);

	void sendAsynchronous(Object o, String topic, String tag);

	void sendSynchronous(Object o, String topic, String tag);
}
