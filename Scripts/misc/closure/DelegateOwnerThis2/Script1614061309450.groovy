import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

/**
 * https://qiita.com/saba1024/items/b57c412961e1a2779881
 */
class Test2 {
	String abc = "abc in Test2"
	def exec(Closure a) {
		a()
	}
}

class Hoge {
	static void main() {
		String abc = "ConsoleScript"
		def test2 = new Test2()
		
		Closure cls1 = { ->
			println "this:         ${this.class.name}"
			println "owner:        ${owner.class.name}"
			println "delegate:     ${delegate.class.name}"
			println "abc:          ${abc}"
			return abc
		}
		assert test2.exec(cls1) == "ConsoleScript"
		
		Closure cls2 = { ->
			println "this:         ${this.class.name}"
			println "owner:        ${owner.class.name}"
			println "delegate:     ${delegate.class.name}"
			println "delegate.abc: ${delegate.abc}"
			return delegate.abc
		}
		cls2.delegate = test2
		assert test2.exec( cls2 ) == "abc in Test2"
	}
}

Hoge.main()