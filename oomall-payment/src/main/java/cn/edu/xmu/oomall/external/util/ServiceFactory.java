package cn.edu.xmu.oomall.external.util;

import cn.edu.xmu.oomall.external.service.IExternalPayment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-16
 * modified 2020-12-13
 */
@Component
@Slf4j
public class ServiceFactory implements InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Value(value = "${oomall.external.payment-service.name}")
	private List<String> paymentServiceNameList;
	@Value(value =  "oomall.external.order-service.name")
	private String orderServiceName;
	@Value(value =  "oomall.external.customer-service.name")
	private String customerServiceName;
	@Value(value =  "oomall.external.afterSale-service.name")
	private String afterSaleServiceName;


	private List<Object> services = new ArrayList<>();
	private Map<String, Object> patternPayServices = new HashMap<>();

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

	public Map<String, Object> getPatternPayServices() {
		return patternPayServices;
	}

	@Override
	public void afterPropertiesSet() {
		for (String s : paymentServiceNameList) {
			patternPayServices.put(s.replace("Impl", ""), applicationContext.getBean(getBeanName(s)));
		}
		services.add(applicationContext.getBean(getBeanName(orderServiceName)));
		services.add(applicationContext.getBean(getBeanName(customerServiceName)));
		services.add(applicationContext.getBean(getBeanName(afterSaleServiceName)));
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
