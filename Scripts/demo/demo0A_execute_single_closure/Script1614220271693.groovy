import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<Closure> closures = WebUI.callTestCase(findTestCase("demo/createPlainClosures"), [:])

closures.get(0).call()
closures.get(1).call()
closures.get(2).call()
