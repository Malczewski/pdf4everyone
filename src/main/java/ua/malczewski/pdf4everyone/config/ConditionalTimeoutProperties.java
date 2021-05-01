package ua.malczewski.pdf4everyone.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ConditionalTimeoutProperties {
	@Value("${webdriver.timeouts.conditionalDelay.pollingEverySeconds:5}")
	private int pollingEverySeconds;

	@Value("${webdriver.timeouts.conditionalDelay.maxSeconds:5}")
	private int maxSeconds;
}
