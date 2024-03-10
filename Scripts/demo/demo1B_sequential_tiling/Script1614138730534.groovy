import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

import com.kazurayam.browserwindowlayout.TilingCellLayoutMetrics
import com.kazurayam.ks.browserlauncher.BrowserLauncher
import com.kazurayam.testclosure.TestClosure
import com.kazurayam.testclosure.TestClosureResult
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Demo"), [:])

TilingCellLayoutMetrics metrics = new TilingCellLayoutMetrics.Builder(tclosures.size()).build()

println "virtualScreenSize(${metrics.getVirtualScreenSize().getWidth()},${metrics.getVirtualScreenSize().getHeight()})"

tclosures.eachWithIndex { tclosure, i ->
	Closure cls = {
		WebDriver driver = new BrowserLauncher.Builder().build().launchChromeDriver()
		tclosure.setDriver(driver)
		// move the browser window to a good position (x,y)
		driver.manage().window().setPosition(metrics.getCellPosition(i))
		print("position(${metrics.getCellPosition(i).getX()},${metrics.getCellPosition(i).getY()}) ")
		// resize the browser window to a good dimension (width, height)
		driver.manage().window().setSize(metrics.getCellDimension(i))
		println("size(${metrics.getCellDimension(i).getWidth()},${metrics.getCellDimension(i).getHeight()})")
		//
		String js = '''return "outerWidth=" + window.outerWidth + ", outerHeight=" + window.outerHeight + ", innerWidth=" + window.innerWidth + ", innerHeight=" + window.innerHeight;'''
		String jsOut = ((JavascriptExecutor)driver).executeScript(js)
		println jsOut
		//
		TestClosureResult result = tclosure.call()
		driver.quit()
		return result
	}
	TestClosureResult rs = cls.call()
}