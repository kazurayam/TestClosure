import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.chrome.ChromeDriver

import com.kazurayam.ks.testclosure.BrowserLauncher
import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureResult
import com.kazurayam.browserwindowlayout.TilingWindowLayoutMetrics
import com.kazurayam.browserwindowlayout.WindowLayoutMetrics
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Demo"), [:])

WindowLayoutMetrics metrics = new TilingWindowLayoutMetrics.Builder(tclosures.size()).build()

tclosures.eachWithIndex { tclosure, i ->
	Closure cls = {
		WebUI.openBrowser("")
		ChromeDriver driver = new BrowserLauncher.Builder().build().launch()
		tclosure.setDriver(driver)
		// move the browser window to a good position (x,y)
		driver.manage().window().setPosition(metrics.getWindowPosition(i))
		// resize the browser window to a good dimension (width, height)
		driver.manage().window().setSize(metrics.getWindowDimension(i))
		//
		TestClosureResult result = tclosure.call()
		WebUI.closeBrowser()
		return result
	}
	TestClosureResult rs = cls.call()
}