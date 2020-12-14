package cn.edu.xmu.oomall.external.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-16
 */
@Component
@Slf4j
public class ServiceFactory implements InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Value(value = "${oomall.external.payment-service.name}")
	private List<String> paymentServiceNameList;


	private List<Object> services = new ArrayList<>();

	public List<Object> getServices() {
		return services;
	}

	public Object get(Class c) {
		if (c == null || !c.isInterface()) {
			return null;
		}
		for (Object o : services) {
			Class[] interfaces = o.getClass().getInterfaces();
			for (Class clz : interfaces) {
				if (clz.equals(c)) {
					log.info(o.getClass().getSimpleName() + " load successfully");
					return o;
				}
			}
		}
		return null;
	}

	@Override
	public void afterPropertiesSet() {
		for (String s : paymentServiceNameList) {
			services.add(applicationContext.getBean(getBeanName(s)));
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private String getBeanName(String className) {
		if (className == null || className.length() == 0) {
			return null;
		}
		return className.substring(0, 1).toLowerCase() + className.substring(1);
	}
}
