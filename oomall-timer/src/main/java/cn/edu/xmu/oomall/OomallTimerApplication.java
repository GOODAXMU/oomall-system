package cn.edu.xmu.oomall;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xincong yao
 * @date 2020-11-21
 */
@SpringBootApplication
@EnableScheduling
public class OomallTimerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OomallTimerApplication.class, args);
	}
}
