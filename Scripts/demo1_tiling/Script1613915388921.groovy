import java.awt.Dimension
import java.awt.Point

import org.openqa.selenium.Keys
import org.openqa.selenium.Point as SelPoint

import com.kazurayam.ks.TestClosuresExecutor
import com.kazurayam.ks.browserwindow.BrowserWindowsLayoutManager
import com.kazurayam.ks.browserwindow.TilingLayoutManager
import com.kms.katalon.core.model.FailureHandling
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

BrowserWindowsLayoutManager layout = new TilingLayoutManager(3)

TestClosuresExecutor executor = new TestClosuresExecutor()

executor.addClosure({
	String url = 'http://demoaut.katalon.com/'
	WebUI.openBrowser('')
	manageLayout.call(layout, 0)
	WebUI.navigateToUrl(url)
	WebUI.waitForPageLoad(5)
	WebUI.comment("processing ${url}")
	TestObject btnMakeAppointment = newTestObjectCSS("#btn-make-appointment")
	WebUI.verifyElementPresent(btnMakeAppointment, 5)
	WebUI.click(btnMakeAppointment)
	TestObject txtUsername = newTestObjectCSS("#txt-username")
	TestObject txtPassword = newTestObjectCSS("#txt-password")
	TestObject btnLogin = newTestObjectCSS("#btn-login")
	WebUI.waitForElementPresent(txtUsername, 5)
	WebUI.setText(txtUsername, "John Doe")
	WebUI.setText(txtPassword, "ThisIsNotAPassword")
	WebUI.click(btnLogin)
	TestObject btnBookAppointment = newTestObjectCSS("#btn-book-appointment")
	WebUI.waitForElementPresent(btnBookAppointment, 3, FailureHandling.STOP_ON_FAILURE)
	WebUI.delay(3)
	WebUI.closeBrowser()
	})

executor.addClosure({
	String url = 'https://forum.katalon.com/'
	WebUI.openBrowser('')
	manageLayout.call(layout, 1)
	WebUI.navigateToUrl(url)
	WebUI.comment("processing ${url}")
	TestObject tObj = newTestObjectXPath("//a[contains(.,'How To Help Us Help You')]")
	WebUI.scrollToElement(tObj, 3)
	WebUI.delay(5)
	WebUI.closeBrowser()
	})

executor.addClosure({
	String url = 'https://duckduckgo.com/'
	WebUI.openBrowser('')
	manageLayout.call(layout, 2)
	WebUI.navigateToUrl(url)
	WebUI.comment("processing ${url}")
	TestObject searchText = newTestObjectXPath("//input[@id='search_form_input_homepage']")
	WebUI.verifyElementPresent(searchText, 10)
	WebUI.sendKeys(searchText, "katalon")
	WebUI.sendKeys(searchText, Keys.chord(Keys.ENTER))
	WebUI.waitForPageLoad(5)
	String title = WebUI.getWindowTitle()
	WebUI.verifyMatch(title, "katalon at DuckDuckGo", true)
	WebUI.delay(3)
	WebUI.closeBrowser()
	})
	
executor.execute()

TestObject newTestObjectXPath(String xpath) {
	TestObject tObj = new TestObject(xpath)
	tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
	return tObj
}

TestObject newTestObjectCSS(String cssSelector) {
	TestObject tObj = new TestObject(cssSelector)
	tObj.addProperty("css", ConditionType.EQUALS, cssSelector)
	return tObj
}