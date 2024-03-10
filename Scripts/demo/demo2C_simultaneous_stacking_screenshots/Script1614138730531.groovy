import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

import com.kazurayam.browserwindowlayout.StackingCellLayoutMetrics
import com.kazurayam.ks.browserlauncher.BrowserLauncher
import com.kazurayam.testclosure.TestClosure
import com.kazurayam.testclosure.TestClosureCollectionExecutor
import com.kazurayam.testclosure.WebDriversContainer
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

BrowserLauncher launcher = new BrowserLauncher.Builder().build()
WebDriversContainer wdc = new WebDriversContainer()
for (int i = 0; i < tclosures.size(); i++) {
	wdc.add(launcher.launchChromeDriver());
}

// create the executor
TestClosureCollectionExecutor executor = 
	new TestClosureCollectionExecutor.Builder()
			.cellLayoutMetrics(
				new StackingCellLayoutMetrics.Builder(tclosures.size())
					.cellDimension(new Dimension(1000, 600))
					.disposition(new Point(120,120))
					.build()
			)
			.build()

// setup the executor what to do
executor.addTestClosures(tclosures)
	
// now do the job
executor.execute()

wdc.quitAll()
