package cn.edu.xmu.oomall;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xincong yao
 * @date 2020-10-31
 */
@SpringBootApplication
@EnableDubbo
public class OomallSeckillApplication {

	public static void main(String[] args) {
		SpringApplication.run(OomallSeckillApplication.class, args);
	}

}
