import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

import com.kazurayam.ks.TestClosuresExecutor
import com.kazurayam.ks.browserwindow.BrowserWindowsLayoutManager
import com.kazurayam.ks.browserwindow.StackingLayoutManager
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def manageLayout = { BrowserWindowsLayoutManager layout, int numOfWindows, int index ->
	Point pos = layout.getWindowPosition(numOfWindows, index)
	Dimension dim = layout.getWindowDimension(numOfWindows, index)
	WebUI.setViewPortSize(dim.width, dim.height)
	Point windowPosition = new Point(pos.x, pos.y)
	DriverFactory.getWebDriver().manage().window().setPosition(windowPosition)
}

BrowserWindowsLayoutManager layout = new StackingLayoutManager.Builder().windowDimension(new Dimension(1024, 500)).build()

TestClosuresExecutor executor = new TestClosuresExecutor()

executor.addClosure({
	String url = 'http://demoaut.katalon.com/'
	WebUI.openBrowser('')
	manageLayout.call(layout, 3, 0)
	WebUI.navigateToUrl(url)
	WebUI.comment("processed ${url}")
	WebUI.delay(5)
	WebUI.closeBrowser()
	})

executor.addClosure({
	String url = 'https://forum.katalon.com/'
	WebUI.openBrowser('')
	manageLayout.call(layout, 3, 1)
	WebUI.navigateToUrl(url)
	WebUI.comment("processed ${url}")
	WebUI.delay(5)
	WebUI.closeBrowser()
	})

executor.addClosure({
	String url = 'https://duckduckgo.com/'
	WebUI.openBrowser('')
	manageLayout.call(layout, 3, 2)
	WebUI.navigateToUrl(url)
	WebUI.comment("processed ${url}")
	WebUI.delay(5)
	WebUI.closeBrowser()
	})
	
executor.execute()
