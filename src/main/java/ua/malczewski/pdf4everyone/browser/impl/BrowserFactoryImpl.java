package ua.malczewski.pdf4everyone.browser.impl;

import lombok.SneakyThrows;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ua.malczewski.pdf4everyone.browser.BrowserFactory;

@Service
public class BrowserFactoryImpl implements BrowserFactory {
	@Autowired
	private Environment environment;

	@SneakyThrows
	public Browser getBrowser() {
		ChromeOptions options = new ChromeOptions()
				.setBinary(environment.getProperty("webdriver.chrome.binary"))
				.setHeadless(true)
				.addArguments("--disable-gpu", "--no-sandbox", "--incognito");
		return Browser.builder()
				.driver(new ChromeDriver(options))
				.build();
	}
}
