import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.windowlayout.TilingLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Demo"), [:])

WindowLayoutMetrics metrics = new TilingLayoutMetrics.Builder().build()

tclosures.eachWithIndex { tclosure, i ->
	tclosure.setWindowLayoutMetrics(metrics)
	tclosure.setWindowLocation(new WindowLocation(tclosures.size(), i))
	tclosure.call()
}