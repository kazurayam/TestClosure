import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kazurayam.ks.windowlayout.StackingWindowLayoutMetrics
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Demo"), [:])

TestClosureCollectionExecutor executor = 
	new TestClosureCollectionExecutor.Builder().
		windowLayoutMetrics(new StackingWindowLayoutMetrics.Builder().
			windowDimension(new Dimension(1280, 800)).
			disposition(new Dimension(0,0)).
			build()).
		build()

executor.addAllClosures(tclosures)
	
executor.execute()
