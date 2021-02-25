import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kazurayam.ks.windowlayout.StackingWindowLayoutMetrics
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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

// load the collection of TestClosures
List<TestClosure> tclosures = WebUI.callTestCase(findTestCase(
	"demo/createTestClosures4fullPageScreenshotReusingWindowForMultipleURLs"), [:])

// create the executor
TestClosureCollectionExecutor executor =
	new TestClosureCollectionExecutor.Builder().
		maxThreads(2).          // maxThreads should be equal to the number of CPU Cores
		windowLayoutMetrics(new StackingWindowLayoutMetrics.Builder().
			windowDimension(new Dimension(1024, 500)).build()).
		build()

// setup the executor what to do
executor.addAllClosures(tclosures)
	
// now do the job
executor.execute()
