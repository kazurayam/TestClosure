import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

import com.kazurayam.browserwindowlayout.StackingCellLayoutMetrics
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
				new StackingCellLayoutMetrics.Builder(tclosures.size())
					.cellDimension(new Dimension(1024, 500))
					.disposition(new Point(120, 120))
					.build()
			)
			.build()

executor.addTestClosures(tclosures)
	
executor.execute()

wdc.quitAll()
