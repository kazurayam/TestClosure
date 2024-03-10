import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import com.kazurayam.ks.browserlauncher.BrowserLauncher
import com.kazurayam.testclosure.TestClosure
import com.kazurayam.testclosure.TestClosureResult
import com.kazurayam.browserwindowlayout.StackingCellLayoutMetrics
import com.kazurayam.browserwindowlayout.CellLayoutMetrics
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

List<TestClosure> tclosures = 
	WebUI.callTestCase(findTestCase("demo/createTestClosures4Screenshooting"), [:])

CellLayoutMetrics metrics = 
	new StackingCellLayoutMetrics.Builder(tclosures.size())
					.cellDimension(new Dimension(1024, 500)).build()

tclosures.eachWithIndex { tclosure, i ->
	Closure cls = {
		WebDriver driver = new BrowserLauncher.Builder().build().launchChromeDriver()
		tclosure.setDriver(driver)
		// move the browser window to a good position (x,y)
		driver.manage().window().setPosition(metrics.getCellPosition(i))
		// resize the browser window to a good dimension (width, height)
		driver.manage().window().setSize(metrics.getCellDimension(i))
		//
		TestClosureResult result = tclosure.call()
		//WebUI.closeBrowser()
		driver.quit()
		return result
	}
	TestClosureResult rs = cls.call()
}