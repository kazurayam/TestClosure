import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


List<Closure> closures = WebUI.callTestCase(findTestCase("demo/createPlainFixture"), [:])

for (int i = 0; i < closures.size(); i++) {
	closures.get(i).call()
}