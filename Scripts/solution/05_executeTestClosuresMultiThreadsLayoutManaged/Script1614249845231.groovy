import org.openqa.selenium.chrome.ChromeDriver

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kazurayam.browserwindowlayout.StackingWindowLayoutMetrics
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// load the collection of TestClosures
List<TestClosure> tclosures = new ArrayList<TestClosure>()

Closure closure = { ChromeDriver driver, String url ->
	DriverFactory.changeWebDriver(driver)
	WebUI.navigateToUrl(url)
	WebUI.waitForPageLoad(5)
}

tclosures.add(new TestClosure(closure, ["http://demoaut.katalon.com/"]))
tclosures.add(new TestClosure(closure, ["https://duckduckgo.com/"]))


// create the executor
TestClosureCollectionExecutor executor =
	new TestClosureCollectionExecutor.Builder().
		numThreads(2).          // maxThreads should be equal to the number of CPU Cores
		windowLayoutMetrics(new StackingWindowLayoutMetrics.Builder(2).build()).
		build()

// setup the executor what to do
executor.addTestClosures(tclosures)
	
// now do the job
executor.execute()
