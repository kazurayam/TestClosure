import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.JavascriptExecutor

import com.kazurayam.ks.testclosure.BrowserLauncher
import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureResult
import com.kazurayam.browserwindowlayout.TilingCellLayoutMetrics
import com.kazurayam.browserwindowlayout.CellLayoutMetrics
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Demo"), [:])

TilingCellLayoutMetrics metrics = new TilingCellLayoutMetrics.Builder(tclosures.size()).build()

println "virtualScreenSize(${metrics.getVirtualScreenSize().getWidth()},${metrics.getVirtualScreenSize().getHeight()})"

tclosures.eachWithIndex { tclosure, i ->
	Closure cls = {
		ChromeDriver driver = new BrowserLauncher.Builder().build().launch()
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