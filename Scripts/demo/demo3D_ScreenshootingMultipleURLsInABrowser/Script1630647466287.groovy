import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime

import com.kazurayam.browserwindowlayout.StackingCellLayoutMetrics
import com.kazurayam.ks.testclosure.BrowserLauncher
import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kazurayam.ks.testclosure.WebDriversContainer
import com.kazurayam.timekeeper.Measurement
import com.kazurayam.timekeeper.Table
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
Measurement beginToFinish = new Measurement.Builder(
	"How long the test took from begining to finish", ["ID"]).build()
tk.add(new Table.Builder(beginToFinish).noLegend().build())

// load the collection of TestClosures
List<TestClosure> tclosures = WebUI.callTestCase(findTestCase(
	"demo/createTestClosures4ScreenshootingMultipleURLsInABrowser"), ["timekeeper": tk])

BrowserLauncher launcher = 
	new BrowserLauncher.Builder(["Katalon"]).build()
	
WebDriversContainer wdc = new WebDriversContainer()
for (int i = 0; i < GlobalVariable.NUM_OF_THREADS; i++) {
	wdc.add(launcher.launchChromeDriver());
}

// create the executor
TestClosureCollectionExecutor executor =
	new TestClosureCollectionExecutor.Builder(wdc)
		.cellLayoutMetrics(
			new StackingCellLayoutMetrics.Builder(GlobalVariable.NUM_OF_THREADS).build())
		.build()

// setup the executor what to do
executor.addTestClosures(tclosures)
	
// now do the job
LocalDateTime beforeExecute = LocalDateTime.now()
executor.execute()
wdc.quitAll()

LocalDateTime afterExecute = LocalDateTime.now()
beginToFinish.recordDuration(["ID": GlobalVariable.ID], beforeExecute, afterExecute)

// compile a performance report
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path testOutput = projectDir.resolve("test-output")
Path reportFile = testOutput.resolve("demo3D_" + GlobalVariable.ID + "_report.md")
Files.createDirectories(reportFile.getParent())
tk.report(reportFile)
