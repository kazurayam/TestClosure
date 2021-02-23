import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.windowlayout.BrowserWindowLayoutKeyword as BrowserWindow
import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation
import com.kms.katalon.core.configuration.RunConfiguration
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
 * the container of Closures to return
 */
List<TestClosure> tclosures = new ArrayList<TestClosure>()

Closure shooter = { WindowLayoutMetrics metrics, WindowLocation location, String url, String fileName ->
	WebUI.openBrowser('')
	BrowserWindow.layout(metrics, location)
	WebUI.navigateToUrl(url)
	WebUI.waitForPageLoad(5)
	WebUI.comment("processing ${url}")
	Path dir = Paths.get(RunConfiguration.getProjectDir()).resolve("screenshots")
	Files.createDirectories(dir)
	Path png = dir.resolve(fileName)
	WebUI.takeScreenshot(png.toString())
	WebUI.closeBrowser()
}

// construct closures
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/", "01_top.png"]))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/katalon-studio", "02_katalon-studio.png"]))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/testops", "03_testops.png"]))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/katalon-recorder-ide", "04_katalon-recorder-ide.png"]))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/web-testing/", "04_web-testing.png"]))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/mobile-testing/", "06_mobile-testing.png"]))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/api-testing/", "07_api-testing"]))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/desktop-testing/", "08_desktop-testing"]))
//tclosures.add(new TestClosure(shooter, ["https://docs.katalon.com/katalon-studio/docs/index.html", "09_docs_index.png"]))
//tclosures.add(new TestClosure(shooter, ["https://forum.katalon.com/", "10_forum.png"]))
//tclosures.add(new TestClosure(shooter, ["https://github.com/katalon-studio/katalon-studio", "11_github_katalon-studio.png"]))


// Here you are
return tclosures

