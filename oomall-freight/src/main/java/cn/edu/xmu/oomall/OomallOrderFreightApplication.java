package cn.edu.xmu.oomall;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xincong yao
 * @date 2020-10-26
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OomallOrderFreightApplication {

	public static void main(String[] args) {
		SpringApplication.run(OomallOrderFreightApplication.class, args);
	}

}
