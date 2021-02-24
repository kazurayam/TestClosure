package com.kazurayam.ks.windowlayout

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory

public class BrowserWindowLayoutKeyword {

	@Keyword
	static void layout(WindowLayoutMetrics metrics, WindowLocation location) {
		WebDriver driver = DriverFactory.getWebDriver()
		if (driver == null) {
			throw new IllegalStateException("driver is null")
		}
		// move the browser window to this position (x,y)
		Point pos = metrics.getWindowPosition(location)
		driver.manage().window().setPosition(pos)
		// resize the browser window to this dimension (width, height)
		Dimension dim = metrics.getWindowDimension(location)
		driver.manage().window().setSize(dim)
	}
}