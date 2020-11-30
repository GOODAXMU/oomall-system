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

	@Value(value = "${oomall.external.customer-service.name}")
	private String customerServiceName;
	@Value(value = "${oomall.external.shop-service.name}")
	private String shopServiceName;
	@Value(value = "${oomall.external.inventory-service.name}")
	private String inventoryServiceName;
	@Value(value = "${oomall.external.freight-service.name}")
	private String freightServiceName;
	@Value(value = "${oomall.external.discount-service.name}")
	private String discountServiceName;
	@Value(value = "${oomall.external.rebate-service.name}")
	private String rebateServiceName;
	@Value(value = "${oomall.external.shipment-service.name}")
	private String shipmentServiceName;
	@Value(value = "${oomall.external.share-service.name}")
	private String shareServiceName;
	@Value(value = "${oomall.external.activity-service.name}")
	private String activityServiceName;
	@Value(value = "${oomall.external.flash-sale-service.name}")
	private String flashSaleServiceName;
	@Value(value = "${oomall.external.seckill-service.name}")
	private String seckillServiceName;
	@Value(value = "${oomall.external.goods-service.name}")
	private String goodsServiceName;

	private List<Object> services = new ArrayList<>();

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
		services.add(applicationContext.getBean(getBeanName(customerServiceName)));
		services.add(applicationContext.getBean(getBeanName(shopServiceName)));
		services.add(applicationContext.getBean(getBeanName(inventoryServiceName)));
		services.add(applicationContext.getBean(getBeanName(freightServiceName)));
		services.add(applicationContext.getBean(getBeanName(discountServiceName)));
		services.add(applicationContext.getBean(getBeanName(rebateServiceName)));
		services.add(applicationContext.getBean(getBeanName(shipmentServiceName)));
		services.add(applicationContext.getBean(getBeanName(shareServiceName)));
		services.add(applicationContext.getBean(getBeanName(activityServiceName)));
		services.add(applicationContext.getBean(getBeanName(flashSaleServiceName)));
		services.add(applicationContext.getBean(getBeanName(seckillServiceName)));
		services.add(applicationContext.getBean(getBeanName(goodsServiceName)));
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
