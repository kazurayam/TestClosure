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
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import org.apache.commons.lang.time.StopWatch

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

Closure shooter = { WindowLayoutMetrics metrics, WindowLocation location, List<Tuple> urlFilePairs ->
	Closure shoot = { url, file ->
		WebUI.navigateToUrl(url)
		WebUI.waitForPageLoad(1, FailureHandling.STOP_ON_FAILURE)
		WebUI.comment("processing ${url}")
		StopWatch stopWatch = new StopWatch()
		stopWatch.start()
		WebUI.takeFullPageScreenshot(file.toString())
		stopWatch.stop()
		println "shooting ${url} took ${stopWatch.getTime() / 1000} seconds"
	}
	WebUI.openBrowser('')
	BrowserWindow.layout(metrics, location)
	urlFilePairs.each { Tuple pair ->
		String url = pair[0]
		Path file = pair[1]
		shoot.call( url, file )
	}
	WebUI.closeBrowser()
}

// clean screenshots dir
Path dir = Paths.get(RunConfiguration.getProjectDir()).resolve("tmp/screenshots")
FileUtils.deleteDirectory(dir.toFile())
Files.createDirectories(dir)

// construct closures
tclosures.add(new TestClosure(shooter, [[
		new Tuple("https://www.katalon.com/", dir.resolve("01_top.png")),
		new Tuple("https://www.katalon.com/katalon-studio", dir.resolve("02_katalon-studio.png")),
		new Tuple("https://www.katalon.com/testops", dir.resolve("03_testops.png")),
		new Tuple("https://www.katalon.com/katalon-recorder-ide", dir.resolve("04_katalon-recorder-ide.png"))
	]]))

tclosures.add(new TestClosure(shooter, [[
	new Tuple("https://www.katalon.com/web-testing/", dir.resolve("05_web-testing.png")),
	new Tuple("https://www.katalon.com/mobile-testing/", dir.resolve("06_mobile-testing.png")),
	new Tuple("https://www.katalon.com/api-testing/", dir.resolve("07_api-testing.png")),
	new Tuple("https://www.katalon.com/desktop-testing/", dir.resolve("08_desktop-testing.png"))
	]]))

// Here you are
return tclosures

