import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kazurayam.ks.windowlayout.StackingWindowLayoutMetrics
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * This script processes multiple Web pages simultaniously.
 * This script opens up to 4 browser windows.
 * 
 * For each URL it does the following:
 * 1. open browser window
 * 2. navigate to the URL
 * 3. take screenshot
 * 4. save the image into file
 * 5. close the window
 */

// load the collection of TestClosures
List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Screenshooting"), [:])

// create the executor
TestClosureCollectionExecutor executor = 
	new TestClosureCollectionExecutor.Builder().
		numThreads(5).
		windowLayoutMetrics(StackingWindowLayoutMetrics.DEFAULT).
		build()

// setup the executor what to do
executor.addTestClosures(tclosures)
	
// now do the job
executor.execute()
