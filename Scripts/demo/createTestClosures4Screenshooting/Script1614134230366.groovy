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
import org.apache.commons.io.FileUtils

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

Closure shooter = { WindowLayoutMetrics metrics, WindowLocation location, String url, Path file ->
	WebUI.openBrowser('')
	BrowserWindow.layout(metrics, location)
	WebUI.navigateToUrl(url)
	WebUI.waitForPageLoad(5)
	WebUI.comment("processing ${url}")
	WebUI.takeScreenshot(file.toString())
	WebUI.closeBrowser()
}

// clean screenshots dir
Path dir = Paths.get(RunConfiguration.getProjectDir()).resolve("tmp/screenshots")
FileUtils.deleteDirectory(dir.toFile())
Files.createDirectories(dir)

// construct closures
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/",
							dir.resolve("01_top.png")] ))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/katalon-studio",
							dir.resolve("02_katalon-studio.png")] ))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/testops", 
							dir.resolve("03_testops.png")] ))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/katalon-recorder-ide",
							dir.resolve("04_katalon-recorder-ide.png")] ))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/web-testing/",
							dir.resolve("05_web-testing.png")] ))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/mobile-testing/", 
							dir.resolve("06_mobile-testing.png")] ))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/api-testing/", 
							dir.resolve("07_api-testing")]))
tclosures.add(new TestClosure(shooter, ["https://www.katalon.com/desktop-testing/", 
							dir.resolve("08_desktop-testing")]))


// Here you are
return tclosures

