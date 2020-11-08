package cn.edu.xmu.oomall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author xincong yao
 * @date 2020-11-1
 */
@EnableConfigurationProperties(RedisProperties.class)
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisProperties {

	private String lockPrefix;
	private long lockExpireTime;
	private long lockWaitTime;
	private int lockAttemptTimes;

}
