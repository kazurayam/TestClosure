import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/*
 * Helper function
 */
TestObject newTestObjectXPath(String xpath) {
	TestObject tObj = new TestObject(xpath)
	tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
	return tObj
}

/*
 * Helper function
 */
TestObject newTestObjectCSS(String cssSelector) {
	TestObject tObj = new TestObject(cssSelector)
	tObj.addProperty("css", ConditionType.EQUALS, cssSelector)
	return tObj
}

/*
 * the container of Closures to return
 */
List<Closure> closures = new ArrayList<Closure>()

// construct closures
closures.add({
	String url = 'http://demoaut.katalon.com/'
	WebUI.openBrowser('')
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

closures.add({
	String url = 'https://forum.katalon.com/'
	WebUI.openBrowser('')
	WebUI.navigateToUrl(url)
	WebUI.comment("processing ${url}")
	TestObject tObj = newTestObjectXPath("//a[contains(.,'How To Help Us Help You')]")
	WebUI.scrollToElement(tObj, 3)
	WebUI.delay(5)
	WebUI.closeBrowser()
})

closures.add({
	String url = 'https://duckduckgo.com/'
	WebUI.openBrowser('')
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

// Here you are
return closures
