package ua.malczewski.pdf4everyone.browser.impl;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ua.malczewski.pdf4everyone.config.ConditionalTimeoutProperties;
import ua.malczewski.pdf4everyone.config.FixedTimeoutProperties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import static java.lang.String.format;

@Slf4j
public class BrowserActions {

	public static BrowserPrepareAction size(int width, int height) {
		return timedAction(format("resizing window to %dx%d", width, height),
				browser -> browser.getDriver().manage().window().setSize(new Dimension(width, height)));
	}

	public static BrowserPrepareAction openUrl(String url) {
		return timedAction(format("opening url %s", url),
				browser -> browser.getDriver().get(url));
	}

	public static BrowserPrepareAction sleep(int delay, FixedTimeoutProperties timeouts) {
		final int resultDelay = Math.min(delay, timeouts.getMaxSeconds());
		return timedAction(format("waiting %d seconds", resultDelay),
				browser -> {
					var wait = new WebDriverWait(browser.getDriver(), Duration.of(resultDelay, ChronoUnit.SECONDS));
					try {
						wait.until(driver -> false);
					} catch (TimeoutException e) {
						// just ignore
					}
				});
	}

	public static BrowserPrepareAction waitForCondition(String conditionVariable, ConditionalTimeoutProperties timeouts) {
		return timedAction(format("waiting for variable '%s", conditionVariable),
				browser -> {
					var wait = new FluentWait<ChromeDriver>(browser.getDriver())
							.withTimeout(Duration.of(timeouts.getMaxSeconds(), ChronoUnit.SECONDS))
							.pollingEvery(Duration.of(timeouts.getPollingEverySeconds(), ChronoUnit.SECONDS));

					wait.until(driver -> Boolean.parseBoolean(
							driver.executeScript("return !!" + conditionVariable).toString()));
				});
	}

	public static BrowserTerminateAction screenshot() {
		return timedTerminateAction("generating screenshot",
				browser -> browser.getDriver().getScreenshotAs(OutputType.BYTES));
	}

	public static BrowserTerminateAction pdf() {
		return timedTerminateAction("generating pdf",
				browser -> Base64.getDecoder().decode(browser.getDriver().executeCdpCommand("Page.printToPDF",
						Maps.newHashMap()).get("data").toString()));
	}

	public static BrowserPrepareAction timedAction(String message, BrowserPrepareAction action) {
		return browser -> {
			log.info("[session:{}]: {} - started", browser.getDriver().getSessionId(), message);
			long startTime = System.currentTimeMillis();
			action.accept(browser);
			log.info("[session:{}]: {} - finished in {}ms", browser.getDriver().getSessionId(), message,
					System.currentTimeMillis() - startTime);
		};
	}

	public static BrowserTerminateAction timedTerminateAction(String message, BrowserTerminateAction action) {
		return browser -> {
			log.info("[session:{}]: {} - started", browser.getDriver().getSessionId(), message);
			long startTime = System.currentTimeMillis();
			var result = action.apply(browser);
			log.info("[session:{}]: {} - finished in {}ms", browser.getDriver().getSessionId(), message,
					System.currentTimeMillis() - startTime);
			return result;
		};
	}
}
