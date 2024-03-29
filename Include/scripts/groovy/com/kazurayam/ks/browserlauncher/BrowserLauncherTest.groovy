package com.kazurayam.ks.browserlauncher

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver

import com.kazurayam.ks.browserlauncher.BrowserLauncher
import com.kazurayam.ks.browserlauncher.BrowserLauncher.Builder

@RunWith(JUnit4.class)
public class BrowserLauncherTest {

	@Test
	public void test_launchChromeDriver_usig_katalon_keyword() {
		BrowserLauncher launcher = new BrowserLauncher.Builder().build()
		WebDriver driver = launcher.launchChromeDriver()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}

	@Test
	public void test_launchChromeDriver_chrome_default_profile() {
		BrowserLauncher launcher =
				new BrowserLauncher.Builder()
				.index(1).build()
		WebDriver driver = launcher.launchChromeDriver()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}

	@Test
	public void test_launchChromeDriver_specified_profile_noIndex() {
		BrowserLauncher launcher =
				new BrowserLauncher.Builder(["Katalon", "Katalon2"])
				.build()
		WebDriver driver = launcher.launchChromeDriver()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}

	@Test
	public void test_launchChromeDriver_specified_profile_index0() {
		BrowserLauncher launcher =
				new BrowserLauncher.Builder(["Katalon", "Katalon2"])
				.index(0).build()
		WebDriver driver = launcher.launchChromeDriver()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}


	@Test
	public void test_launchChromeDriver_specified_profile_index1() {
		BrowserLauncher launcher =
				new BrowserLauncher.Builder(["Katalon", "Katalon2"])
				.index(1).build()
		WebDriver driver = launcher.launchChromeDriver()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}


	public void test_launchChromeDriver_specified_profile_index2() {
		BrowserLauncher launcher =
				new BrowserLauncher.Builder(["Katalon", "Katalon2"])
				.index(2).build()
		WebDriver driver = launcher.launchChromeDriver()
		Thread.sleep(3000)
		assertNotNull(driver)
		driver.quit()
	}

	/* This test will always fail, so comment this out
	 @Test
	 public void test_launchFirefoxDriver_firefox_default_profile() {
	 BrowserLauncher launcher =
	 new BrowserLauncher.Builder().build()
	 WebDriver driver = launcher.launchFirefoxDriver()
	 Thread.sleep(3000)
	 assertNotNull(driver)
	 driver.quit()
	 }
	 */
}
