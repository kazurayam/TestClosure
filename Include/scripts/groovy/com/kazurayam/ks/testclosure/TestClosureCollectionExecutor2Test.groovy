package com.kazurayam.ks.testclosure

import static org.junit.Assert.*

import java.time.LocalDateTime

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver

@RunWith(JUnit4.class)
public class TestClosureCollectionExecutor2Test {

	WebDriversContainer wdc
	TestClosureCollectionExecutor2 executor

	@Before
	void setup() {
		BrowserLauncher launcher = new BrowserLauncher.Builder().build()
		wdc = new WebDriversContainer()
		wdc.add(launcher.launchChromeDriver())
		wdc.add(launcher.launchChromeDriver())
		wdc.add(launcher.launchChromeDriver())
		executor = new TestClosureCollectionExecutor2.Builder(wdc).build()
		List<TestClosure> tclosures = new ArrayList<TestClosure>()
		tclosures.add(new TestClosure({ WebDriver driver, String name ->
			println "Hello, ${name}!"
		}, ["World"]))
		executor.addTestClosures(tclosures)
	}
	
	@Test
	void test_getNumThreads() {
		int mt = executor.getNumThreads()
		assertTrue("expected number of threads to be 3, but was ${mt}", 3 == mt)
	}

	@Test
	void test_resolveIndex() {
		def input    = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
		def expected = [0, 1, 2, 0, 1, 2, 0, 1, 2, 0]
		input.eachWithIndex { value, index ->
			int actual = executor.resolveIndex(value)
			assertTrue("actual=${actual} for value=${value}, expected ${expected.get(index)}", actual == expected.get(index))
		}
	}

	@Test
	void test_size() {
		assertTrue(executor.size() == 1)
	}

	@After
	void tearDown() {
		//executor.closeBrowsers()
		wdc.quitAll()
	}
}
