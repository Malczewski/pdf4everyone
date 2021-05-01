package ua.malczewski.pdf4everyone.browser.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.openqa.selenium.chrome.ChromeDriver;

@Data
@Builder
@AllArgsConstructor
public class Browser {
	private ChromeDriver driver;

	public void destroy() {
		driver.quit();
	}
}
