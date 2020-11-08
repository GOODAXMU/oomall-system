package cn.edu.xmu.oomall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author xincong yao
 * @date 2020-11-6
 */
@EnableConfigurationProperties(SeckillProperties.class)
@ConfigurationProperties(prefix = "seckill")
@Data
public class SeckillProperties {

	private String version;
	private double loadFactor;
	private int attemptTimes;
}
