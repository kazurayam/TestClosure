import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kazurayam.browserwindowlayout.TilingCellLayoutMetrics
import com.kazurayam.ks.testclosure.BrowserLauncher
import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor2
import com.kazurayam.ks.testclosure.WebDriversContainer
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Demo"), [:])

BrowserLauncher launcher = new BrowserLauncher.Builder().build()
WebDriversContainer wdc = new WebDriversContainer()
for (int i = 0; i < tclosures.size(); i++) {
	wdc.add(launcher.launchChromeDriver());
}

TestClosureCollectionExecutor2 executor = 
	new TestClosureCollectionExecutor2.Builder(wdc)
			.cellLayoutMetrics(
				new TilingCellLayoutMetrics.Builder(tclosures.size())
					.build()
			).build()

executor.addTestClosures(tclosures)

executor.execute()

wdc.quitAll()