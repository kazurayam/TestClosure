import java.awt.Dimension
import java.awt.Point

import org.openqa.selenium.Keys
import org.openqa.selenium.Point as SelPoint

import com.kazurayam.ks.TestClosuresExecutor
import com.kazurayam.ks.browserwindow.BrowserWindowsLayoutManager
import com.kazurayam.ks.browserwindow.StackingLayoutManager
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def manageLayout = { BrowserWindowsLayoutManager layout, int tileIndex ->
	Point pos = layout.getPosition(tileIndex)
	Dimension dim = layout.getDimension(tileIndex)
	WebUI.setViewPortSize((int)dim.width, (int)dim.height)
	SelPoint windowPosition = new SelPoint((int)pos.x, (int)pos.y)
	DriverFactory.getWebDriver().manage().window().setPosition(windowPosition)
}

BrowserWindowsLayoutManager layout = new StackingLayoutManager(3, new Dimension(1024, 500))

List<Closure> closures = new ArrayList<Closure>()

closures.add({
	String url = 'http://demoaut.katalon.com/'
	WebUI.openBrowser('')
	manageLayout.call(layout, 0)
	WebUI.navigateToUrl(url)
	WebUI.comment("processed ${url}")
	WebUI.delay(5)
	WebUI.closeBrowser()
	})

closures.add({
	String url = 'https://forum.katalon.com/'
	WebUI.openBrowser('')
	manageLayout.call(layout, 1)
	WebUI.navigateToUrl(url)
	WebUI.comment("processed ${url}")
	WebUI.delay(5)
	WebUI.closeBrowser()
	})

closures.add({
	String url = 'https://duckduckgo.com/'
	WebUI.openBrowser('')
	manageLayout.call(layout, 2)
	WebUI.navigateToUrl(url)
	WebUI.comment("processed ${url}")
	WebUI.delay(5)
	WebUI.closeBrowser()
	})
	
TestClosuresExecutor executor = new TestClosuresExecutor()
executor.addClosures(closures)
executor.execute()
