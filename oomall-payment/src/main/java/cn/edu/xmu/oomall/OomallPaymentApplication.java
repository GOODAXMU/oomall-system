package cn.edu.xmu.oomall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xincong yao
 * @date 2020-11-8
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OomallPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(OomallPaymentApplication.class, args);
	}
}