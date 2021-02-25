import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kazurayam.ks.windowlayout.BrowserWindowLayoutKeyword as BrowserWindow
import com.kazurayam.ks.windowlayout.StackingWindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// load the collection of TestClosures
List<TestClosure> tclosures = new ArrayList<TestClosure>()

Closure closure = { WindowLayoutMetrics metrics, WindowLocation location, String url ->
	WebUI.openBrowser('')
	BrowserWindow.layout(metrics, location)
	WebUI.navigateToUrl(url)
	WebUI.waitForPageLoad(10)
	WebUI.delay(3)
	WebUI.closeBrowser()
}

tclosures.add(new TestClosure(closure, ["http://demoaut.katalon.com/"]))
tclosures.add(new TestClosure(closure, ["https://duckduckgo.com/"]))


// create the executor
TestClosureCollectionExecutor executor =
	new TestClosureCollectionExecutor.Builder().
		maxThreads(2).          // maxThreads should be equal to the number of CPU Cores
		windowLayoutMetrics(StackingWindowLayoutMetrics.DEFAULT).
		build()

// setup the executor what to do
executor.addTestClosures(tclosures)
	
// now do the job
executor.execute()
