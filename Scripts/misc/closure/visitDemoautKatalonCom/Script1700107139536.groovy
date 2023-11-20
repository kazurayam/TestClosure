import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * Test Cases/misc/closure/visitDemoautKatalonCom
 */
/*
 * Helper function
 */
TestObject newTestObjectCSS(String cssSelector) {
	TestObject tObj = new TestObject(cssSelector)
	tObj.addProperty("css", ConditionType.EQUALS, cssSelector)
	return tObj
}

String url = 'http://demoaut.katalon.com/'
WebUI.openBrowser('')
WebUI.navigateToUrl(url)
//WebUI.waitForPageLoad(5)
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
//WebUI.delay(1)
WebUI.closeBrowser()