import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kazurayam.ks.testclosure.StackingLayoutMetrics
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


TestClosureCollectionExecutor executor = 
	new TestClosureCollectionExecutor(new StackingLayoutMetrics.Builder().build())

List<Closure> closures = WebUI.callTestCase(findTestCase("demo/createFixture"), [:])

executor.addAllClosures(closures)
	
executor.execute()
