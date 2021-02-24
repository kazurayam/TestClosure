import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


List<Closure> closures = WebUI.callTestCase(findTestCase("demo/createPlainClosures"), [:])

closures.each { closure ->
	closure.call()
}