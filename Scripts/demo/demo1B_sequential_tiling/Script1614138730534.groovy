import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.WebDriver

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureResult
import com.kazurayam.ks.windowlayout.TilingWindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Demo"), [:])

WindowLayoutMetrics metrics = TilingWindowLayoutMetrics.DEFAULT

tclosures.eachWithIndex { tclosure, i ->
	Closure cls = {
		WebUI.openBrowser("")
		WebDriver driver = DriverFactory.getWebDriver()
		tclosure.setDriver(driver)
		WindowLocation location = new WindowLocation(tclosures.size(), i)
		// move the browser window to a good position (x,y)
		driver.manage().window().setPosition(metrics.getWindowPosition(location))
		// resize the browser window to a good dimension (width, height)
		driver.manage().window().setSize(metrics.getWindowDimension(location))
		//
		TestClosureResult result = tclosure.call()
		WebUI.closeBrowser()
		return result
	}
	TestClosureResult rs = cls.call()
}