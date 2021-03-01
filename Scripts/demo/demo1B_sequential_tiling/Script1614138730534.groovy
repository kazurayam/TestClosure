import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.windowlayout.TilingWindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Demo"), [:])

WindowLayoutMetrics metrics = TilingWindowLayoutMetrics.DEFAULT

tclosures.eachWithIndex { tclosure, i ->
	WindowLocation location = new WindowLocation(tclosures.size(), i)
	tclosure.setPosition(metrics.getWindowPosition(location))
	tclosure.setDimension(metrics.getWindowDimension(location))
	tclosure.call()
}