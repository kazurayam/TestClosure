import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kazurayam.timekeeper.Timekeeper
import com.kazurayam.browserwindowlayout.StackingWindowLayoutMetrics

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration

import internal.GlobalVariable
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

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

// create the executor
TestClosureCollectionExecutor executor =
	new TestClosureCollectionExecutor.Builder()
		.numThreads(GlobalVariable.NUM_OF_THREADS)          // numThreads should be equal to the number of CPU Cores
		.userProfiles(["Katalon", "Katalon2"])
		.windowLayoutMetrics(StackingWindowLayoutMetrics.DEFAULT)
		.build()

// setup the executor what to do
executor.addTestClosures(tclosures)
	
// now do the job
executor.execute()

// compile a performance report
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path reportFile = projectDir.resolve("tmp/demo3D_report.md")
Files.createDirectories(reportFile.getParent())
tk.report(reportFile)
