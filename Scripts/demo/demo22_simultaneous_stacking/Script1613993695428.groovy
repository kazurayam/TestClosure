import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension

import com.kazurayam.ks.windowlayout.StackingLayoutMetrics
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

TestClosureCollectionExecutor executor = new TestClosureCollectionExecutor(
		new StackingLayoutMetrics.Builder().windowDimension(new Dimension(1024, 600)).build()
		)

List<Closure> closures = WebUI.callTestCase(findTestCase("demo/createManagedFixture"), [:])

executor.addAllClosures(closures)
	
executor.execute()
