package ua.malczewski.pdf4everyone.browser.impl;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import ua.malczewski.pdf4everyone.browser.BrowserFactory;
import ua.malczewski.pdf4everyone.browser.BrowserService;
import ua.malczewski.pdf4everyone.config.ConditionalTimeoutProperties;
import ua.malczewski.pdf4everyone.config.FixedTimeoutProperties;
import ua.malczewski.pdf4everyone.rest.api.dto.ExportParameters;

import javax.annotation.PostConstruct;

@Service
public class BrowserServiceImpl implements BrowserService {

	@Autowired
	@Qualifier("browserExecutor")
	ThreadPoolTaskExecutor executor;

	@Autowired
	private Environment environment;
	@Autowired
	private BrowserFactory browserFactory;
	@Autowired
	private FixedTimeoutProperties fixedTimeoutProperties;
	@Autowired
	private ConditionalTimeoutProperties conditionalTimeoutProperties;

	@PostConstruct
	private void init() {
		System.setProperty("webdriver.chrome.driver", ensureFilePath("webdriver.chrome.driver"));
		ensureFilePath("webdriver.chrome.binary");
	}

	private String ensureFilePath(String propName) {
		String value =  environment.getProperty(propName);
		if (StringUtils.isBlank(value))
			throw new IllegalArgumentException(propName + " should be provided");
		if (!FileUtils.getFile(value).exists())
			throw new IllegalArgumentException("File doesn't exist: " + value);
		return value;
	}

	@SneakyThrows
	@Override
	public byte[] exportScreenshot(ExportParameters parameters) {
		BrowserFlow flow = getFlowBuilder(parameters).perform(BrowserActions.screenshot());
		return executor.submit(() -> flow.process(browserFactory.getBrowser())).get();
	}

	@SneakyThrows
	@Override
	public byte[] exportPDF(ExportParameters parameters) {
		BrowserFlow flow = getFlowBuilder(parameters).perform(BrowserActions.pdf());
		return executor.submit(() -> flow.process(browserFactory.getBrowser())).get();
	}

	private BrowserFlow.BrowserFlowBuilder getFlowBuilder(ExportParameters parameters) {
		return BrowserFlow.builder()
				.addStep(BrowserActions.size(parameters.getWidth(), parameters.getHeight()))
				.addStep(BrowserActions.openUrl(parameters.getUrl()))
				.addStepIf(StringUtils.isNotBlank(parameters.getIndicatorVariable()),
						BrowserActions.waitForCondition(parameters.getIndicatorVariable(), conditionalTimeoutProperties))
				.addStepIf(parameters.getDelaySeconds() > 0,
						BrowserActions.sleep(parameters.getDelaySeconds(), fixedTimeoutProperties));
	}

}
