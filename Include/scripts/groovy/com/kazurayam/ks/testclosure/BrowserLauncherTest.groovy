package com.kazurayam.ks.testclosure

import static org.junit.Assert.*

import java.time.LocalDateTime

import org.junit.Ignore
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver

@RunWith(JUnit4.class)
public class BrowserLauncherTest {

	@Test
	public void test_launch_usig_katalon_keyword() {
		BrowserLauncher launcher = new BrowserLauncher.Builder().build()
		WebDriver driver = launcher.launch()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}

	@Test
	public void test_launch_chrome_default_profile() {
		BrowserLauncher launcher =
				new BrowserLauncher.Builder()
				.index(1).build()
		WebDriver driver = launcher.launch()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}

	@Test
	public void test_launch_chrome_specified_profile_noIndex() {
		BrowserLauncher launcher =
				new BrowserLauncher.Builder(["Katalon", "Katalon_alt"])
				.build()
		WebDriver driver = launcher.launch()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}

	@Test
	public void test_launch_chrome_specified_profile_index0() {
		BrowserLauncher launcher =
				new BrowserLauncher.Builder(["Katalon", "Katalon_alt"])
				.index(0).build()
		WebDriver driver = launcher.launch()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}


	@Test
	public void test_launch_chrome_specified_profile_index1() {
		BrowserLauncher launcher =
				new BrowserLauncher.Builder(["Katalon", "Katalon_alt"])
				.index(1).build()
		WebDriver driver = launcher.launch()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}

	
	public void test_launch_chrome_specified_profile_index2() {
		BrowserLauncher launcher =
				new BrowserLauncher.Builder(["Katalon", "Katalon_alt"])
				.index(2).build()
		WebDriver driver = launcher.launch()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}
}
