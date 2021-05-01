package ua.malczewski.pdf4everyone.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FixedTimeoutProperties {
	@Value("${webdriver.timeouts.fixedDelay.maxSeconds:5}")
	private int maxSeconds;
}
