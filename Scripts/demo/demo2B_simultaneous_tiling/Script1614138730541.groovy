import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kazurayam.browserwindowlayout.TilingCellLayoutMetrics
import com.kazurayam.ks.browserlauncher.BrowserLauncher
import com.kazurayam.testclosure.TestClosure
import com.kazurayam.testclosure.TestClosureCollectionExecutor
import com.kazurayam.testclosure.WebDriversContainer
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Demo"), [:])

BrowserLauncher launcher = new BrowserLauncher.Builder().build()
WebDriversContainer wdc = new WebDriversContainer()
for (int i = 0; i < tclosures.size(); i++) {
	wdc.add(launcher.launchChromeDriver());
}

TestClosureCollectionExecutor executor = 
	new TestClosureCollectionExecutor.Builder()
			.cellLayoutMetrics(
				new TilingCellLayoutMetrics.Builder(tclosures.size())
					.build()
			).build()

executor.addTestClosures(tclosures)

executor.execute()

wdc.quitAll()