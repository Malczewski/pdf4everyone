package ua.malczewski.pdf4everyone.browser.impl;

import com.google.common.collect.Maps;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;

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

	public static BrowserPrepareAction sleep(long delay) {
		return browser -> browser.getDriver().manage().timeouts().implicitlyWait(Duration.of(delay, ChronoUnit.MILLIS));
	}


	public static BrowserTerminateAction screenshot() {
		return browser -> browser.getDriver().getScreenshotAs(OutputType.BYTES);
	}

	public static BrowserTerminateAction pdf() {
		return browser -> Base64.getDecoder().decode(browser.getDriver().executeCdpCommand("Page.printToPDF",
					Maps.newHashMap()).get("data").toString());
	}
}
