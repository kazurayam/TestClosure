import org.openqa.selenium.Keys
import org.openqa.selenium.chrome.ChromeDriver

import com.kazurayam.ks.testclosure.TestClosure
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

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
List<TestClosure> tclosures = new ArrayList<TestClosure>()

// construct closures
tclosures.add(new TestClosure({ ChromeDriver driver ->
	DriverFactory.changeWebDriver(driver)
	String url = 'http://demoaut.katalon.com/'
	WebUI.navigateToUrl(url, FailureHandling.OPTIONAL)
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
	}, [])
)

tclosures.add(new TestClosure({ ChromeDriver driver ->
	DriverFactory.changeWebDriver(driver)
	String url = 'https://forum.katalon.com/'
	WebUI.navigateToUrl(url)
	WebUI.comment("processing ${url}")
	TestObject tObj = newTestObjectXPath("//a[contains(text(),'How To Help Us Help You')]")
	WebUI.verifyElementPresent(tObj, 5)
	WebUI.scrollToElement(tObj, 5)
	}, [])
)

tclosures.add(new TestClosure({ ChromeDriver driver ->
	DriverFactory.changeWebDriver(driver)
	String url = 'https://duckduckgo.com/'
	WebUI.navigateToUrl(url)
	WebUI.comment("processing ${url}")
	TestObject searchText = newTestObjectXPath("//input[@id='searchbox_input']")
	WebUI.verifyElementPresent(searchText, 10)
	WebUI.sendKeys(searchText, "katalon")
	WebUI.sendKeys(searchText, Keys.chord(Keys.ENTER))
	WebUI.waitForPageLoad(1)
	String title = WebUI.getWindowTitle()
	WebUI.verifyMatch(title, "katalon at DuckDuckGo", true)
	}, [])
)

// Here you are
return tclosures
