package ua.malczewski.pdf4everyone.browser.impl;

import com.google.common.collect.Maps;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import ua.malczewski.pdf4everyone.config.ConditionalTimeoutProperties;
import ua.malczewski.pdf4everyone.config.FixedTimeoutProperties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

public class BrowserActions {

	public static BrowserPrepareAction size(int width, int height) {
		return browser -> browser.getDriver().manage().window().setSize(new Dimension(width, height));
	}

	public static BrowserPrepareAction openUrl(String url) {
		return browser -> browser.getDriver().get(url);
	}

	public static BrowserPrepareAction sleep(int delay, FixedTimeoutProperties timeouts) {
		final int resultDelay = Math.min(delay, timeouts.getMaxSeconds());
		return browser -> browser.getDriver().manage().timeouts()
				.implicitlyWait(Duration.of(resultDelay, ChronoUnit.SECONDS));
	}

	public static BrowserPrepareAction waitForCondition(String conditionVariable, ConditionalTimeoutProperties timeouts) {
		return browser -> {
			Wait<ChromeDriver> wait = new FluentWait<>(browser.getDriver())
					.withTimeout(Duration.of(timeouts.getMaxSeconds(), ChronoUnit.SECONDS))
					.pollingEvery(Duration.of(timeouts.getPollingEverySeconds(), ChronoUnit.SECONDS));
			wait.until(driver -> Boolean.parseBoolean(
					driver.executeScript("return !!document." + conditionVariable).toString()));
		};
	}

	public static BrowserTerminateAction screenshot() {
		return browser -> browser.getDriver().getScreenshotAs(OutputType.BYTES);
	}

	public static BrowserTerminateAction pdf() {
		return browser -> Base64.getDecoder().decode(browser.getDriver().executeCdpCommand("Page.printToPDF",
					Maps.newHashMap()).get("data").toString());
	}
}
