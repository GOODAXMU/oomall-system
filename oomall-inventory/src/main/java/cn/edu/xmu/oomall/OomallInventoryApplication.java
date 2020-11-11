package cn.edu.xmu.oomall;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xincong yao
 * @date 2020-11-8
 */
@SpringBootApplication
@EnableDubbo
public class OomallInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OomallInventoryApplication.class, args);
	}
}