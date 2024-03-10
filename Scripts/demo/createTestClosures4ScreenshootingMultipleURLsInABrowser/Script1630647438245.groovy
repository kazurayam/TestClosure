import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime

import org.apache.commons.io.FileUtils
//import org.apache.commons.lang.time.StopWatch

import org.openqa.selenium.WebDriver

import com.kazurayam.ashotwrapper.AShotWrapper
import com.kazurayam.ashotwrapper.AShotWrapper.Options
import com.kazurayam.testclosure.TestClosure
import com.kazurayam.timekeeper.Measurement
import com.kazurayam.timekeeper.Timekeeper
import com.kazurayam.timekeeper.Table
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// the caller Test Case should pass an instance of Timekeeper as runtime parameter
assert timekeeper != null

Measurement navigation = new Measurement.Builder(
	"How long it took to navigate to URLs", ["URL"]).build()
timekeeper.add(new Table.Builder(navigation)
	.noLegend().build())	
timekeeper.add(new Table.Builder(navigation)
	.sortByAttributes().thenByDuration().noLegend().build())

Measurement screenshot = new Measurement.Builder(
	"How long it took to take screenshots", ["URL"]).build()
timekeeper.add(new Table.Builder(screenshot)
	.noLegend().build())
timekeeper.add(new Table.Builder(screenshot)
	.sortByAttributes().thenByDuration().noLegend().build())

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
		LocalDateTime beforeNavi = LocalDateTime.now()
		//
		WebUI.navigateToUrl(url, FailureHandling.OPTIONAL)
		LocalDateTime afterNavi = LocalDateTime.now()
		navigation.recordDuration(["URL": url],
			beforeNavi, afterNavi)
		WebUI.comment("navigate ${url} took ${navigation.getLastRecordDurationMillis() / 1000} seconds")
		
		LocalDateTime beforeScreenshot = LocalDateTime.now()
		Options opt = new Options.Builder().timeout(100).build()
		AShotWrapper.saveEntirePageImage(driver, opt, file.toFile())
		LocalDateTime afterScreenshot = LocalDateTime.now()
		screenshot.recordSizeAndDuration(["URL": url],
			file.toFile().length(), beforeScreenshot, afterScreenshot)
		WebUI.comment("screenshot ${url} took ${screenshot.getLastRecordDurationMillis() / 1000} secs")		
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
		new Tuple("https://www.modular.com/", dir.resolve("11_mojular.png")),
		new Tuple("https://www.modular.com/engine", dir.resolve("12_modular_engine.png")),
		new Tuple("https://www.modular.com/mojo", dir.resolve("13_modular_mojo.png")),
		new Tuple("https://www.modular.com/blog", dir.resolve("14_modular_blog.png"))
	]
	]))

tclosures.add(new TestClosure(shooter, [
	[
		new Tuple("https://playwright.dev/", dir.resolve("15_playwright.png")),
		new Tuple("https://playwright.dev/docs/intro", dir.resolve("16_playwright_docs_intro.png")),
		new Tuple("https://playwright.dev/docs/api/class-playwright", dir.resolve("17_playwright_docs_api.png")),
		new Tuple("https://playwright.dev/community/welcome", dir.resolve("18_playwritht_community.png"))
	]
	]))

/*
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
*/

// Here you are
return tclosures

