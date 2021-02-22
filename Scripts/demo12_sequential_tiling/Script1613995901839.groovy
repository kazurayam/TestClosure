import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver

import com.kazurayam.ks.browserwindow.BrowserWindowsLayoutManager
import com.kazurayam.ks.browserwindow.TilingLayoutManager
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


BrowserWindowsLayoutManager layoutManager = new TilingLayoutManager.Builder().build()

def manageLayout = { BrowserWindowsLayoutManager layout, int capacity, int index ->
	WebDriver driver = DriverFactory.getWebDriver()
	// move the browser window to this position (x,y)
	Point pos = layout.getWindowPosition(capacity, index)
	driver.manage().window().setPosition(pos)
	// resize the browser window to thiis dimension (width, height)
	Dimension dim = layout.getWindowDimension(capacity, index)
	driver.manage().window().setSize(dim)
}


List<Closure> closures = WebUI.callTestCase(findTestCase("createTestClosures"), [:])

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
			//Point pos = layoutManager.getWindowPosition(closures.size(), i)
			//Dimension dim = layoutManager.getWindowDimension(closures.size(), i)
			//println "window pos=(${pos.x},${pos.y}), width=${dim.width}, height=${dim.height}"
			manageLayout.call(layoutManager, closures.size(), i)
		}
		return result
	}
	
	closures.get(i).call()
}