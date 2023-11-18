import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.browserwindowlayout.StackingCellLayoutMetrics
import com.kazurayam.ks.testclosure.BrowserLauncher
import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor2
import com.kazurayam.ks.testclosure.WebDriversContainer
import com.kazurayam.timekeeper.Timekeeper
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable


/**
 * This script processes multiple Web pages simultaniously.
 * This script opens up to 2 browser windows.
 *
 * This script runs quicker than demo2C because it performs less number of opening/closing windows.
 * 
 * For each URL it does the following:
 * 1. open browser window
 * 2. navigate to the URL
 * 3. take screenshot
 * 4. save the image into file
 * 5. close the window
 * 
 * In each window, a browser window opens.
 * In a window, process multiple URLs sequentially without closing the window.
 * 
 */

Timekeeper tk = new Timekeeper()

// load the collection of TestClosures
List<TestClosure> tclosures = WebUI.callTestCase(findTestCase(
	"demo/createTestClosures4ScreenshootingMultipleURLsInABrowser"), ["timekeeper": tk])

BrowserLauncher launcher = 
	new BrowserLauncher.Builder(["Katalon", "Katalon2"]).build()
	
WebDriversContainer wdc = new WebDriversContainer()
for (int i = 0; i < GlobalVariable.NUM_OF_THREADS; i++) {
	wdc.add(launcher.launchChromeDriver());
}

// create the executor
TestClosureCollectionExecutor2 executor =
	new TestClosureCollectionExecutor2.Builder(wdc)
		.cellLayoutMetrics(
			new StackingCellLayoutMetrics.Builder(GlobalVariable.NUM_OF_THREADS).build())
		.build()

// setup the executor what to do
executor.addTestClosures(tclosures)
	
// now do the job
executor.execute()

wdc.quitAll()

// compile a performance report
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path reportFile = projectDir.resolve("tmp/demo3D_report.md")
Files.createDirectories(reportFile.getParent())
tk.report(reportFile)
