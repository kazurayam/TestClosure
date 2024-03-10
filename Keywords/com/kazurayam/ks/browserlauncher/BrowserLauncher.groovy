package com.kazurayam.ks.browserlauncher

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

import com.kazurayam.webdriverfactory.UserProfile
import com.kazurayam.webdriverfactory.chrome.ChromeDriverFactory
import com.kazurayam.webdriverfactory.firefox.FirefoxDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

/**
 * 
 */
public class BrowserLauncher {

	private int numThreads
	private List<String> userProfiles
	private int index

	private BrowserLauncher(Builder builder) {
		this.userProfiles = builder.userProfiles
		this.index = builder.index
	}

	ChromeDriver launchChromeDriver() {
		System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverPath())
		ChromeDriver driver = null
		ChromeDriverFactory cdFactory = ChromeDriverFactory.newChromeDriverFactory()
		if (userProfiles.size() > 0) {
			int x = index % userProfiles.size()
			String profileName = userProfiles.get(x)
			driver = cdFactory.newChromeDriver(new UserProfile(profileName)).getDriver()
		} else {
			// if userProfiles is empty, then open browser without any profile
			driver = cdFactory.newChromeDriver().getDriver()
		}
		return driver
	}

	FirefoxDriver launchFirefoxDriver() {
		System.setProperty("webdriver.firefox.driver", DriverFactory.getGeckoDriverPath())
		FirefoxDriver driver = null
		FirefoxDriverFactory fdFactory = FirefoxDriverFactory.newFirefoxDriverFactory()
		if (userProfiles.size() > 0) {
			int x = index % userProfiles.size()
			String profileName = userProfiles.get(x)
			driver = fdFactory.newFirefoxDriver(new UserProfile(profileName)).getDriver()
		} else {
			// if userProfiles is empty, then open browser without any profile
			driver = fdFactory.newFirefoxDriver().getDriver()
		}
		return driver
	}

	public static class Builder {
		private List<String> userProfiles = []
		private int index = 0

		Builder() {
			this(new ArrayList<String>())
		}

		Builder(List<String> userProfiles) {
			Objects.requireNonNull(userProfiles)
			this.userProfiles = userProfiles
		}

		Builder index(int index) {
			if (index < 0) {
				throw new IllegalArgumentException("index=${index} must be >=0")
			}
			this.index = index
			return this
		}

		BrowserLauncher build() {
			return new BrowserLauncher(this)
		}
	}
}