package com.kazurayam.ks.testclosure;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import com.kazurayam.browserwindowlayout.TilingCellLayoutMetrics

@RunWith(JUnit4.class)
public class WebDriversContainerTest {

	WebDriversContainer wdc;

	@Before
	public void setup() {
		BrowserLauncher launcher = new BrowserLauncher.Builder().build()
		wdc = new WebDriversContainer();
		wdc.add(launcher.launchChromeDriver());
		wdc.add(launcher.launchChromeDriver());
		wdc.add(launcher.launchChromeDriver());
		wdc.add(launcher.launchChromeDriver());
		TilingCellLayoutMetrics metrics = new TilingCellLayoutMetrics.Builder(wdc.size()).build()
		for (int i = 0; i < wdc.size(); i++) {
			WebDriver driver = wdc.get(i);
			driver.manage().window().setPosition(metrics.getCellPosition(i))
			driver.manage().window().setSize(metrics.getCellDimension(i))
		}
		Thread.sleep(1000)
	}

	@After
	public void tearDown() {
		wdc.quitAll()
	}

	@Test
	public void test_size() {
		Thread.sleep(1000)
		assertEquals(4, wdc.size());
	}
}