package ua.malczewski.pdf4everyone.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class SpringConfig {

	@Autowired
	Environment environment;

	@Bean(name = "browserExecutor")
	public ThreadPoolTaskExecutor getBrowserExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Integer.parseInt(environment.getProperty("executor.pool.corePoolSize", "3")));
		executor.setMaxPoolSize(Integer.parseInt(environment.getProperty("executor.pool.maxPoolSize", "6")));
		executor.setQueueCapacity(Integer.parseInt(environment.getProperty("executor.pool.queueCapacity", "100")));
		executor.setKeepAliveSeconds(Integer.parseInt(environment.getProperty("executor.pool.keepAliveSeconds", "30")));
		executor.setThreadNamePrefix(environment.getProperty("executor.pool.threadPrefix"));
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}
}
