package com.kazurayam.ks.testclosure

import org.openqa.selenium.WebDriver

public class WebDriversContainer {

	private final List<WebDriver> drivers;

	public WebDriversContainer() {
		drivers = new ArrayList<WebDriver>()
	}

	public WebDriversContainer add(WebDriver driver) {
		drivers.add(driver)
		return this
	}

	public WebDriver get(int index) {
		return drivers.get(index)
	}

	public int size() {
		return drivers.size()
	}

	public Iterator<WebDriver> iterator() {
		return drivers.iterator()
	}

	public void quitAll() {
		Iterator iter = this.iterator();
		while (iter.hasNext()) {
			WebDriver driver = iter.next()
			driver.quit()
		}
	}
}
