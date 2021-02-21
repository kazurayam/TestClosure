import org.openqa.selenium.Keys

import com.kazurayam.ks.TestClosuresExecutor
import com.kazurayam.ks.browserwindow.BrowserWindowLayoutManager
import com.kazurayam.ks.browserwindow.TilingLayoutManager
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import java.awt.Point
import java.awt.Dimension
import org.openqa.selenium.Point as SelPoint

def manageLayout = { BrowserWindowLayoutManager layout, int tileIndex ->
	Point pos = layout.getPosition(tileIndex)
	Dimension dim = layout.getDimension(tileIndex)
	WebUI.setViewPortSize((int)dim.width, (int)dim.height)
	SelPoint windowPosition = new SelPoint((int)pos.x, (int)pos.y)
	DriverFactory.getWebDriver().manage().window().setPosition(windowPosition)
}

BrowserWindowLayoutManager layout = new TilingLayoutManager(3)

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
	TestObject searchText = new TestObject()
	searchText.addProperty("css", ConditionType.EQUALS, "#search_form_input_homepage")
	WebUI.verifyElementPresent(searchText, 10)
	WebUI.sendKeys(searchText, "katalon")
	WebUI.sendKeys(searchText, Keys.chord(Keys.ENTER))
	WebUI.waitForPageLoad(5)
	String title = WebUI.getWindowTitle()
	WebUI.verifyMatch(title, "katalon at DuckDuckGo", true)
	WebUI.closeBrowser()
	})
	
TestClosuresExecutor executor = new TestClosuresExecutor()
executor.addClosures(closures)
executor.execute()


