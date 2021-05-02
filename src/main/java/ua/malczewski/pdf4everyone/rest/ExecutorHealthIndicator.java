package ua.malczewski.pdf4everyone.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnEnabledHealthIndicator("executor")
public class ExecutorHealthIndicator implements HealthIndicator {

	@Autowired
	@Qualifier("browserExecutor")
	ThreadPoolTaskExecutor executor;

	@Override
	public Health health() {
		return Health.up().withDetail("maxPoolSize", executor.getMaxPoolSize())
				.withDetail("poolSize", executor.getPoolSize())
				.withDetail("activeCount", executor.getActiveCount())
				.withDetail("queueSize", executor.getThreadPoolExecutor().getQueue().size())
				.withDetail("queueCapacity", executor.getThreadPoolExecutor().getQueue().size()
						+ executor.getThreadPoolExecutor().getQueue().remainingCapacity())
				.build();

	}
}
