package cn.edu.xmu.oomall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xincong yao
 * @date 2020-11-27
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OomallOrderConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OomallOrderConsumerApplication.class, args);
	}
}
