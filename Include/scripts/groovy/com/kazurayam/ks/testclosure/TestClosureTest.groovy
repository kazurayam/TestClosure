package com.kazurayam.ks.testclosure

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

@RunWith(JUnit4.class)
public class TestClosureTest {

	@Before
	void setup() {}

	/**
	 * No Exception expected
	 */
	@Test
	void test_closure_pass() {
		Closure cl = { WebDriver driver ->
			print "${driver.toString()}"
		}
		TestClosure tc = new TestClosure(cl, [])
		WebUI.openBrowser('')
		WebDriver driver = DriverFactory.getWebDriver()
		tc.setDriver(driver)
		tc.call()
		WebUI.closeBrowser()
	}


	@Test
	void test_closure_1st_parameter_type() {
		try {
			Closure cl = { String arg0, String arg1 ->
				print "${arg0},${arg1}"
			}
			TestClosure tc = new TestClosure(cl, [])
			fail("should fail, but did not")
		} catch (IllegalArgumentException e) {
			assertTrue("msg=" + e.getMessage(), e.getMessage().contains("WebDriver"))
		}
	}
}
