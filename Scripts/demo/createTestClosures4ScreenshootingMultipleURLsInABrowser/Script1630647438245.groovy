import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.windowlayout.BrowserWindowLayoutKeyword as BrowserWindow
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.apache.commons.io.FileUtils
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import org.apache.commons.lang.time.StopWatch


import org.openqa.selenium.WebDriver
import com.kazurayam.ashotwrapper.AShotWrapper;
import com.kazurayam.ashotwrapper.AShotWrapper.Options;


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

Closure shooter = { WebDriver driver, List<Tuple> urlFilePairs ->
	Objects.requireNonNull(driver)
	DriverFactory.changeWebDriver(driver)
	urlFilePairs.each { Tuple pair ->
		String url = pair[0]
		Path file = pair[1]
		//
		StopWatch stopWatch = new StopWatch()
		stopWatch.start()
		//
		WebUI.comment("navigating to ${url}")
		WebUI.navigateToUrl(url)
		stopWatch.suspend()
		WebUI.comment("navigation to ${url} took ${stopWatch.getTime() / 1000} seconds")
		stopWatch.reset();
		stopWatch.start();
		WebUI.comment("saving image of ${url}")
		
		//WebUI.takeFullPageScreenshot(file.toString())
		//WebUI.takeScreenshot(file.toString())
		Options opt = new Options.Builder().timeout(100).build()
		AShotWrapper.saveEntirePageImage(driver, opt, file.toFile())
		
		stopWatch.stop()
		WebUI.comment("saving image of ${url} took ${stopWatch.getTime() / 1000} seconds")
	}
}

// clean screenshots dir
Path dir = Paths.get(RunConfiguration.getProjectDir()).resolve("tmp/screenshots")
FileUtils.deleteDirectory(dir.toFile())
Files.createDirectories(dir)

// construct closures
tclosures.add(new TestClosure(shooter, [
	[
		new Tuple("https://www.katalon.com/", dir.resolve("01_top.png")),
		new Tuple("https://www.katalon.com/katalon-studio", dir.resolve("02_katalon-studio.png")),
		new Tuple("https://www.katalon.com/testops", dir.resolve("03_testops.png")),
		new Tuple("https://www.katalon.com/katalon-recorder-ide", dir.resolve("04_katalon-recorder-ide.png"))
	]
	]))

tclosures.add(new TestClosure(shooter, [
	[
		new Tuple("https://www.katalon.com/web-testing/", dir.resolve("05_web-testing.png")),
		new Tuple("https://www.katalon.com/mobile-testing/", dir.resolve("06_mobile-testing.png")),
		new Tuple("https://www.katalon.com/api-testing/", dir.resolve("07_api-testing.png")),
		new Tuple("https://www.katalon.com/desktop-testing/", dir.resolve("08_desktop-testing.png"))
	]
	]))

tclosures.add(new TestClosure(shooter, [
	[
		new Tuple("https://www.katalon.com/", dir.resolve("11_top.png")),
		new Tuple("https://www.katalon.com/katalon-studio", dir.resolve("12_katalon-studio.png")),
		new Tuple("https://www.katalon.com/testops", dir.resolve("13_testops.png")),
		new Tuple("https://www.katalon.com/katalon-recorder-ide", dir.resolve("14_katalon-recorder-ide.png"))
	]
	]))

tclosures.add(new TestClosure(shooter, [
	[
		new Tuple("https://www.katalon.com/web-testing/", dir.resolve("15_web-testing.png")),
		new Tuple("https://www.katalon.com/mobile-testing/", dir.resolve("16_mobile-testing.png")),
		new Tuple("https://www.katalon.com/api-testing/", dir.resolve("17_api-testing.png")),
		new Tuple("https://www.katalon.com/desktop-testing/", dir.resolve("18_desktop-testing.png"))
	]
	]))

tclosures.add(new TestClosure(shooter, [
	[
		new Tuple("https://www.katalon.com/", dir.resolve("21_top.png")),
		new Tuple("https://www.katalon.com/katalon-studio", dir.resolve("22_katalon-studio.png")),
		new Tuple("https://www.katalon.com/testops", dir.resolve("23_testops.png")),
		new Tuple("https://www.katalon.com/katalon-recorder-ide", dir.resolve("24_katalon-recorder-ide.png"))
	]
	]))

tclosures.add(new TestClosure(shooter, [
	[
		new Tuple("https://www.katalon.com/web-testing/", dir.resolve("25_web-testing.png")),
		new Tuple("https://www.katalon.com/mobile-testing/", dir.resolve("26_mobile-testing.png")),
		new Tuple("https://www.katalon.com/api-testing/", dir.resolve("27_api-testing.png")),
		new Tuple("https://www.katalon.com/desktop-testing/", dir.resolve("28_desktop-testing.png"))
	]
	]))

tclosures.add(new TestClosure(shooter, [
	[
		new Tuple("https://www.katalon.com/", dir.resolve("31_top.png")),
		new Tuple("https://www.katalon.com/katalon-studio", dir.resolve("32_katalon-studio.png")),
		new Tuple("https://www.katalon.com/testops", dir.resolve("33_testops.png")),
		new Tuple("https://www.katalon.com/katalon-recorder-ide", dir.resolve("34_katalon-recorder-ide.png"))
	]
	]))

tclosures.add(new TestClosure(shooter, [
	[
		new Tuple("https://www.katalon.com/web-testing/", dir.resolve("35_web-testing.png")),
		new Tuple("https://www.katalon.com/mobile-testing/", dir.resolve("36_mobile-testing.png")),
		new Tuple("https://www.katalon.com/api-testing/", dir.resolve("37_api-testing.png")),
		new Tuple("https://www.katalon.com/desktop-testing/", dir.resolve("38_desktop-testing.png"))
	]
	]))

// Here you are
return tclosures

