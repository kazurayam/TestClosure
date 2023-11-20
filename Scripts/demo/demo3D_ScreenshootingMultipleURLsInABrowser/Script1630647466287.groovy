import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver

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
 * This script opens multiple browser windows.
 * The number of windows are given by GlobalVariable
 * Java Threads are invoked for each browser window.
 * Each Threds executes a TestClosure.
 * A TestClsore contains a single Groovy Closure where you can implement anything. 
 * A closure of this script will repeat for multiple URLs:
 *     1. navigate to a URL
 *     2. take a full page screenshot
 *     3. save the image into file.
 * This script measures the processing duration and compiles a report in Mardown text format
 */

//============================ init stage ===================================
Timekeeper tk = new Timekeeper()
Measurement beginToFinish = new Measurement.Builder(
	"How long the test took from begining to finish", ["ID"]).build()
tk.add(new Table.Builder(beginToFinish).noLegend().build())

// start a watch to measure the processing duration
LocalDateTime beforeExecute = LocalDateTime.now()


//============================ prepare TestClosures and Executor ==============

// load the collection of TestClosures
List<TestClosure> tclosures = WebUI.callTestCase(findTestCase(
	"demo/createTestClosures4ScreenshootingMultipleURLsInABrowser"), ["timekeeper": tk])

BrowserLauncher launcher = new BrowserLauncher.Builder(["Katalon"]).build()
	
// The number of threads is given by GlobalVariable, we will open the equal number of browser windows 

// open browser windows
WebDriversContainer wdc = new WebDriversContainer()
for (int i = 0; i < GlobalVariable.NUM_OF_THREADS; i++) {
	WebDriver wd = launcher.launchChromeDriver() 
	wdc.add(wd);
}

// create the executor
TestClosureCollectionExecutor executor =
	new TestClosureCollectionExecutor.Builder(wdc)
		.cellLayoutMetrics(
			new StackingCellLayoutMetrics.Builder(GlobalVariable.NUM_OF_THREADS)
				.cellDimension(new Dimension(1000, 800))
				.disposition(new Point(80, 80))
				.build())
		.build()

// setup the executor what to do
executor.addTestClosures(tclosures)



//================================ mapping stage ==============================

// let's do the job
executor.execute()

// close all browser windows
wdc.quitAll()



//================================ reduce stage ===============================
// stop the watch
LocalDateTime afterExecute = LocalDateTime.now()
beginToFinish.recordDuration(["ID": GlobalVariable.ID], beforeExecute, afterExecute)

// compile a performance report
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path testOutput = projectDir.resolve("test-output")
Path reportFile = testOutput.resolve("demo3D_" + GlobalVariable.ID + "_report.md")
Files.createDirectories(reportFile.getParent())
tk.report(reportFile)
