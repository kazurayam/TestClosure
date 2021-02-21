import org.openqa.selenium.Keys

import com.kazurayam.ks.TestClosuresExecutor
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

TestClosuresExecutor executor = new TestClosuresExecutor()

executor.addClosure({
	String url = 'http://demoaut.katalon.com/'
	WebUI.openBrowser('')
	WebUI.navigateToUrl(url)
	WebUI.comment("processed ${url}")
	WebUI.delay(10)
	WebUI.closeBrowser()
})

executor.addClosure({
	String url = 'https://forum.katalon.com/'
	WebUI.openBrowser('')
	WebUI.navigateToUrl(url)
	WebUI.comment("processed ${url}")
	WebUI.delay(10)
	WebUI.closeBrowser()
})

executor.addClosure({
	String url = 'https://duckduckgo.com/'
	WebUI.openBrowser('')
	WebUI.navigateToUrl(url)
	WebUI.comment("processed ${url}")
	TestObject searchText = new TestObject()
	searchText.addProperty("css", ConditionType.EQUALS, "#search_form_input_homepage")
	WebUI.verifyElementPresent(searchText, 10)
	WebUI.sendKeys(searchText, "katalon")
	WebUI.sendKeys(searchText, Keys.chord(Keys.ENTER))
	WebUI.waitForPageLoad(10)
	String title = WebUI.getWindowTitle()
	WebUI.verifyMatch(title, "katalon at DuckDuckGo", true)
	WebUI.closeBrowser()
})

executor.execute()


