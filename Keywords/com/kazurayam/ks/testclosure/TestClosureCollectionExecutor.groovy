package com.kazurayam.ks.testclosure

import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

import com.kazurayam.browserwindowlayout.TilingWindowLayoutMetrics
import com.kazurayam.browserwindowlayout.WindowLayoutMetrics
import com.kazurayam.browserwindowlayout.WindowLocation

import com.kazurayam.webdriverfactory.chrome.ChromeDriverFactory

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.apache.commons.lang.time.StopWatch
import org.openqa.selenium.chrome.ChromeDriver


/**
 * 
 * @author kazurayam
 */
public class TestClosureCollectionExecutor {

	public static final int THREADS_LIMIT = 8
	private int capacity

	private final WindowLayoutMetrics metrics
	private final List<Closure> loadedTestClosures
	private final BrowserLauncher

	private final int numThreads
	private List<String> userProfiles


	private TestClosureCollectionExecutor(Builder builder) {
		this.metrics = builder.metrics
		this.loadedTestClosures = new ArrayList<Closure>()
		this.numThreads = builder.numThreads
		this.userProfiles = builder.userProfiles
	}

	public int getNumThreads() {
		return numThreads
	}

	public int size() {
		return loadedTestClosures.size()
	}

	public void addTestClosures(List<TestClosure> tclosures) {
		capacity = (tclosures.size() > numThreads) ? numThreads : tclosures.size()
		for (int i = 0; i < tclosures.size(); i++) {
			BrowserLauncher launcher = new BrowserLauncher.Builder(userProfiles)
					.index(i).build()
			this.loadTestClosure(tclosures.get(i), launcher)
		}
	}

	private resolveIndex(int i) {
		return i % numThreads
	}

	/**
	 * start a browser for each TestClosures and setup them ready to invoke
	 * 
	 * @param seq ID of the Test Closure 0,1,2,3...
	 * @param tc TestClosure object
	 */
	private void loadTestClosure(TestClosure tc, BrowserLauncher browserLauncher) {
		Objects.requireNonNull(tc)
		Objects.requireNonNull(browserLauncher)
		int index = resolveIndex(this.loadedTestClosures.size())
		WindowLocation location = new WindowLocation(capacity, index)
		// the position (x,y) to which browser window should be moved to
		Point position = metrics.getWindowPosition(location)
		// the size (width, height) to which browser window should be resized to
		Dimension dimension = metrics.getWindowDimension(location)
		//
		Closure cls = {
			// open a browser window for this TestClosure
			ChromeDriver driver = browserLauncher.launch()
			// move the browser window to a good position (x,y)
			driver.manage().window().setPosition(position)
			// resize the browser window to a good dimension (width, height)
			driver.manage().window().setSize(dimension)
			// pass the WebDriver instance to this TestClosure
			tc.setDriver(driver)
			// execute the TestClosure
			TestClosureResult result = tc.call()
			// close the browser
			driver.quit()
			//
			return result
		}
		this.loadedTestClosures.add(cls)
	}

	/**
	 * 
	 */
	public void execute() {
		int size = loadedTestClosures.size()
		if (size == 0) {
			throw new IllegalStateException("should add one or more TestClosure objects")
		}

		// create Thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(capacity)

		List<Future<String>> futures = executorService.invokeAll(loadedTestClosures)
		for (ft in futures) {
			try {
				/* calling the get() method while the task is running will 
				 * cause execution to block until the task finishes properly
				 * executed and the result is available
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
		}ß
	}


	/**
	 * Builder pattern by "Effective Java"
	 */
	public static class Builder {
		// Required parameters - none
		// Optional parameters - initialized to default values
		private WindowLayoutMetrics metrics = new TilingWindowLayoutMetrics.Builder().build()
		private List<TestClosure> testClosures = new ArrayList<TestClosure>()
		private int numThreads = 1
		private List<String> userProfiles = []
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
		Builder userProfiles(List<String> userProfiles) {
			if (userProfiles.size() == 0) {
				throw new IllegalArgumentException("userProfiles must not be empty")
			}
			this.userProfiles = userProfiles
			this.numThreads = userProfiles.size()
			return this
		}
		TestClosureCollectionExecutor build() {
			return new TestClosureCollectionExecutor(this)
		}
	}
}