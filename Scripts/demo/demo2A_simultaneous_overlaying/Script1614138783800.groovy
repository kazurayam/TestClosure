import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kazurayam.browserwindowlayout.StackingCellLayoutMetrics
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Demo"), [:])

TestClosureCollectionExecutor executor = 
	new TestClosureCollectionExecutor.Builder().
		windowLayoutMetrics(
			new StackingCellLayoutMetrics.Builder(tclosures.size())
				.cellDimension(new Dimension(1280, 800))
				.disposition(new Point(0,0))
				.build()
		).build()

executor.addTestClosures(tclosures)
	
executor.execute()
