package ua.malczewski.pdf4everyone.browser;

import org.openqa.selenium.chrome.ChromeDriver;
import ua.malczewski.pdf4everyone.browser.impl.Browser;

public interface BrowserFactory {
	Browser getBrowser();
}
