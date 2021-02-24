import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.openqa.selenium.Dimension

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.windowlayout.StackingWindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<TestClosure> tclosures = WebUI.callTestCase(findTestCase("demo/createTestClosures4Screenshooting"), [:])

WindowLayoutMetrics metrics = new StackingWindowLayoutMetrics.Builder().windowDimension(new Dimension(1024, 500)).build()

tclosures.eachWithIndex { tclosure, i ->
	tclosure.setWindowLayoutMetrics(metrics)
	tclosure.setWindowLocation(new WindowLocation(tclosures.size(), i))
	tclosure.call()
}