package cn.edu.xmu.oomall.external.service;

/**
 * @author xincong yao
 * @date 2020-11-27
 */
public interface IMessageSender {

	void sendOneWay(Object o, String topic);

	void sendAsynchronous(Object o, String topic);

	void sendSynchronous(Object o, String topic);
}
