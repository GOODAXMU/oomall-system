package cn.edu.xmu.oomall.util;

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
 * @date 2020-11-19
 */
@Component
@Slf4j
public class ServiceFactory implements InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;

	public Object get(String c) {
		String[] s = c.split("\\.");
		c = s[s.length - 1];
		return applicationContext.getBean(getBeanName(c));
	}

	@Override
	public void afterPropertiesSet() {

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
