import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kazurayam.ks.windowlayout.TilingLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


WindowLayoutMetrics metrics = new TilingLayoutMetrics.Builder().build()

List<Closure> closures = WebUI.callTestCase(findTestCase("demo/createDemoTestClosures"), [:])

for (int i = 0; i < closures.size(); i++) {
	WindowLocation location = new WindowLocation(closures.size(), i)
	closures.get(i).call(metrics, location)
}