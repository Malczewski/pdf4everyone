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
import ua.malczewski.pdf4everyone.rest.api.dto.ExportParameters;

import javax.annotation.PostConstruct;
import java.util.concurrent.Future;

@Service
public class BrowserServiceImpl implements BrowserService {

	@Autowired
	@Qualifier("browserExecutor")
	ThreadPoolTaskExecutor executor;

	@Autowired
	private Environment environment;
	@Autowired
	private BrowserFactory browserFactory;

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
		BrowserFlow flow = BrowserFlow.builder()
				.addStep(BrowserActions.size(parameters.getWidth(), parameters.getHeight()))
				.addStep(BrowserActions.openUrl(parameters.getUrl()))
				.addStep(BrowserActions.sleep(parameters.getDelayMs()))
				.finish(BrowserActions.screenshot());
		Future<byte[]> resultFuture = executor.submit(() -> {
			return flow.process(browserFactory.getBrowser());
		});
		return resultFuture.get();
	}

	@SneakyThrows
	@Override
	public byte[] exportPDF(ExportParameters parameters) {
		BrowserFlow flow = BrowserFlow.builder()
				.addStep(BrowserActions.size(parameters.getWidth(), parameters.getHeight()))
				.addStep(BrowserActions.openUrl(parameters.getUrl()))
				.addStep(BrowserActions.sleep(parameters.getDelayMs()))
				.finish(BrowserActions.pdf());
		Future<byte[]> resultFuture = executor.submit(() -> {
			return flow.process(browserFactory.getBrowser());
		});
		return resultFuture.get();
	}

}
