package com.kazurayam.testclosure

import static org.junit.Assert.*

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

import io.github.bonigarcia.wdm.WebDriverManager

@RunWith(JUnit4.class)
public class TestClosureTest {

	@BeforeClass
	static void beforeClass() {
		WebDriverManager.chromedriver().setup()
	}

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
		WebDriver driver = new ChromeDriver()
		tc.setDriver(driver)
		tc.call()
		driver.quit()
	}

	@Test
	void test_ChromeDriver() {
		try {
			Closure cl = { ChromeDriver driver ->
				print "${driver.toString()}"
			}
			TestClosure tc = new TestClosure(cl, [])
		} catch (IllegalArgumentException e) {
			fail(e.toString());
		}
	}

	@Test
	void test_FirefoxDriver() {
		try {
			Closure cl = { FirefoxDriver driver ->
				print "${driver.toString()}"
			}
			TestClosure tc = new TestClosure(cl, [])
		} catch (IllegalArgumentException e) {
			fail(e.toString());
		}
	}

	@Test
	void test_String_As_1st_parameter_should_throw_IllegalArgumentException() {
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
