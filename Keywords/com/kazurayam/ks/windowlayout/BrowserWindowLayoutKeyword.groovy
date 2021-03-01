package com.kazurayam.ks.windowlayout

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory

public class BrowserWindowLayoutKeyword {

	@Keyword
	static void layout(Point position, Dimension dimension) {
		Objects.requireNonNull(position)
		Objects.requireNonNull(dimension)
		WebDriver driver = DriverFactory.getWebDriver()
		if (driver == null) {
			throw new IllegalStateException("driver is null")
		}
		// move the browser window to this position (x,y)
		driver.manage().window().setPosition(position)
		// resize the browser window to this dimension (width, height)
		driver.manage().window().setSize(dimension)
	}
}
