package com.kazurayam.testclosure;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import com.kazurayam.browserwindowlayout.TilingCellLayoutMetrics

import io.github.bonigarcia.wdm.WebDriverManager

@RunWith(JUnit4.class)
public class WebDriversContainerTest {

	WebDriversContainer wdc;

	@BeforeClass
	public static void beforeClass() {
		WebDriverManager.chromedriver().setup()
	}

	@Before
	public void setup() {
		wdc = new WebDriversContainer();
		wdc.add(new ChromeDriver());
		wdc.add(new ChromeDriver());
		wdc.add(new ChromeDriver());
		wdc.add(new ChromeDriver());
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