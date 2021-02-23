import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver

import com.kazurayam.ks.testclosure.BrowserWindowsLayoutMetrics
import com.kazurayam.ks.testclosure.TilingLayoutMetrics
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


BrowserWindowsLayoutMetrics layoutMetrics = new TilingLayoutMetrics.Builder().build()

def manageLayout = { BrowserWindowsLayoutMetrics layout, int capacity, int index ->
	WebDriver driver = DriverFactory.getWebDriver()
	// move the browser window to this position (x,y)
	Point pos = layout.getWindowPosition(capacity, index)
	driver.manage().window().setPosition(pos)
	// resize the browser window to thiis dimension (width, height)
	Dimension dim = layout.getWindowDimension(capacity, index)
	driver.manage().window().setSize(dim)
}


List<Closure> closures = WebUI.callTestCase(findTestCase("demo/createFixture"), [:])

for (int i = 0; i < closures.size(); i++) {
	
	WebUI.metaClass.'static'.invokeMethod = { String name, args ->
		def result
		try {
			result = delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
		} catch (Exception e) {
			System.out.println("Handling exception for method \'$name\'")
		}
		// modify WebUI.openBrowser() method 
		if (name == "openBrowser") {
			// move and resize the browser window
			manageLayout.call(layoutMetrics, closures.size(), i)
		}
		return result
	}
	
	closures.get(i).call()
}