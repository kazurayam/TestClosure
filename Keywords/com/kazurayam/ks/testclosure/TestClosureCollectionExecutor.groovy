package com.kazurayam.ks.testclosure

import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

import com.kazurayam.ks.windowlayout.TilingWindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebDriver

/**
 * 
 * @author kazurayam
 */
public class TestClosureCollectionExecutor {

	public static final int THREADS_LIMIT = 8

	private final int numThreads
	private final WindowLayoutMetrics metrics
	private final List<TestClosure> testClosures
	private List<WebDriver> drivers

	private int capacity

	private TestClosureCollectionExecutor(Builder builder) {
		this.numThreads = builder.numThreads
		this.metrics = builder.metrics
		this.testClosures = new ArrayList<TestClosure>()
		this.drivers = new ArrayList<WebDriver>()
	}

	public int getNumThreads() {
		return numThreads
	}

	public int size() {
		return testClosures.size()
	}

	public void addTestClosures(List<TestClosure> tclosures) {
		capacity = (tclosures.size() > numThreads) ? numThreads : tclosures.size()
		for (int i = 0; i < tclosures.size(); i++) {
			this.addTestClosure(tclosures.get(i))
		}
	}

	private resolveIndex(int i) {
		return i % numThreads
	}

	/*
	 * start a browser for each TestClosures and setup them ready to invoke
	 */
	private void addTestClosure(TestClosure tc) {
		Objects.requireNonNull(tc)

		int index = resolveIndex(this.testClosures.size())

		WindowLocation location = new WindowLocation(capacity, index)

		// the position (x,y) to which browser window should be moved to
		Point position = metrics.getWindowPosition(location)

		// the size (width, height) to which browser window should be resized to
		Dimension dimension = metrics.getWindowDimension(location)

		// open a browser window for this TestClosure
		WebUI.openBrowser('')

		WebDriver driver = DriverFactory.getWebDriver()

		// move the browser window to a good position (x,y)
		driver.manage().window().setPosition(position)

		// resize the browser window to a good dimension (width, height)
		driver.manage().window().setSize(dimension)

		// store the reference to the WebDriver instance into a list, so that we can quit them in the end
		drivers.add(driver)

		// pass the WebDriver instance to this TestClosure
		tc.setDriver(driver)

		this.testClosures.add(tc)
	}

	/**
	 * 
	 */
	public void execute() {
		int size = testClosures.size()
		if (size < 1) {
			throw new IllegalStateException("should add one or more TestClosure objects")
		}

		// create Thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(
				(size > numThreads) ?  numThreads : size)

		List<Future<String>> futures = executorService.invokeAll(testClosures)

		for (ft in futures) {
			try {
				/* calling the get() method while the task is
				 * running will cause execution to block
				 * until the task properly execut4s and
				 * the result is available
				 */
				TestClosureResult result = ft.get()
				// how to make use of TestClosureResult? ... should study more
				//println result
			} catch (InterruptedException e) {
				e.printStackTrace()
			} catch (ExecutionException e) {
				// re-throw com.kms.katalon.core.exception.StepFailedException
				// raised by some WebUI.* keywords in the Closure
				throw e
			}
		}

		executorService.shutdown()
		try {
			if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				executorService.shutdownNow()
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow()
		}

		closeBrowsers();
	}

	/*
	 *  close all browsers
	 */
	void closeBrowsers() {
		drivers.forEach({ WebDriver driver ->
			driver.quit()
		})
	}



	/**
	 * Builder pattern by "Effective Java"
	 */
	public static class Builder {
		// Required parameters - none

		// Optional parameters - initialized to default values
		private WindowLayoutMetrics metrics = new TilingWindowLayoutMetrics.Builder().build()
		private List<TestClosure> testClosures = new ArrayList<TestClosure>()
		private int numThreads = 2

		Builder() {}

		Builder windowLayoutMetrics(WindowLayoutMetrics metrics) {
			this.metrics = metrics
			return this
		}

		Builder numThreads(int numThreads) {
			if (numThreads <= 0) {
				throw new IllegalArgumentException("numThreads=${numThreads} must not be less or equal to 0")
			}
			if (numThreads > THREADS_LIMIT) {
				throw new IllegalArgumentException("numThreds=${numThreads} must not be greater than ${THREADS_LIMIT}")
			}
			this.numThreads = numThreads
			return this
		}

		TestClosureCollectionExecutor build() {
			return new TestClosureCollectionExecutor(this)
		}
	}

}
