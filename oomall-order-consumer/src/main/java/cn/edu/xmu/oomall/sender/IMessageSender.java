package cn.edu.xmu.oomall.sender;

/**
 * @author xincong yao
 * @date 2020-12-7
 */
public interface IMessageSender {

	void sendOneWay(Object o, String topic);

	void sendAsynchronous(Object o, String topic);

	void sendSynchronous(Object o, String topic);
}
