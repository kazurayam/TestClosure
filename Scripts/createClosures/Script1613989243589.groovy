import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kazurayam.ks.browserwindow.BrowserWindowsLayoutManager

def manageLayout = { BrowserWindowsLayoutManager layout, int numOfWindows, int tileIndex ->
	Point pos = layout.getWindowPosition(numOfWindows, tileIndex)
	Dimension dim = layout.getWindowDimension(numOfWindows, tileIndex)
	WebUI.setViewPortSize((int)dim.width, (int)dim.height)
	SelPoint windowPosition = new SelPoint((int)pos.x, (int)pos.y)
	DriverFactory.getWebDriver().manage().window().setPosition(windowPosition)
}

List<Closure> closures = new ArrayList<Closure>()

closures.add({
	String url = 'http://demoaut.katalon.com/'
	WebUI.openBrowser('')
	manageLayout.call(layout, 4, 0)
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
	
})

closures.add({
	
})

return closures

/**
 * 
 */
TestObject newTestObjectXPath(String xpath) {
	TestObject tObj = new TestObject(xpath)
	tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
	return tObj
}

/**
 * 
 */
TestObject newTestObjectCSS(String cssSelector) {
	TestObject tObj = new TestObject(cssSelector)
	tObj.addProperty("css", ConditionType.EQUALS, cssSelector)
	return tObj
}