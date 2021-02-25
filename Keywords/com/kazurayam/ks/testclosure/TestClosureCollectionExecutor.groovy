package com.kazurayam.ks.testclosure

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver

import com.kazurayam.ks.windowlayout.TilingWindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * 
 * @author kazurayam
 */
public class TestClosureCollectionExecutor {

	private final int maxThreads
	private final WindowLayoutMetrics metrics
	private final List<TestClosure> testClosures

	private int capacity

	public int getMaxThreads() {
		return maxThreads
	}

	public resolveIndex(int i) {
		return i % maxThreads
	}

	public int size() {
		return testClosures.size()
	}

	public void addTestClosures(List<TestClosure> tclosures) {
		capacity = (tclosures.size() > maxThreads) ? maxThreads : tclosures.size()
		for (int i = 0; i < tclosures.size(); i++) {
			int index = resolveIndex(i)
			this.addTestClosure(tclosures.get(i), index)
		}
	}

	private void addTestClosure(TestClosure tclosure, int index) {
		Objects.requireNonNull(tclosure)
		tclosure.setWindowLayoutMetrics(metrics)
		tclosure.setWindowLocation(new WindowLocation(capacity, index))
		this.testClosures.add(tclosure)
	}

	public void execute() {
		int size = testClosures.size()
		if (size < 1) {
			throw new IllegalStateException("should add one or more TestClosure objects")
		}

		// create Thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(
				(size > maxThreads) ?  maxThreads : size)

		List<Future<String>> futures = executorService.invokeAll(testClosures)

		for (ft in futures) {
			String result = null
			try {
				/* calling the get() method while the task is
				 * running will cause execution to block
				 * until the task properly execut4s and
				 * the result is available
				 */
				result = ft.get()
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace()
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
	}

	/**
	 * Builder pattern by "Effective Java"
	 */
	public static class Builder {
		// Required parameters - none

		// Optional parameters - initialized to default values
		private WindowLayoutMetrics metrics = new TilingWindowLayoutMetrics.Builder().build()
		private List<TestClosure> testClosures = new ArrayList<TestClosure>()
		private int maxThreads = 4

		Builder() {}

		Builder windowLayoutMetrics(WindowLayoutMetrics metrics) {
			this.metrics = metrics
			return this
		}

		Builder maxThreads(int maxThreads) {
			if (maxThreads <= 0) {
				throw new IllegalArgumentException("maxThreads=${maxThreads} must not be less or equal to 0")
			}
			if (maxThreads > 16) {
				throw new IllegalArgumentException("maxThreds=${maxThreads} must not be greater than 16")
			}
			this.maxThreads = maxThreads
			return this
		}

		TestClosureCollectionExecutor build() {
			return new TestClosureCollectionExecutor(this)
		}
	}

	private TestClosureCollectionExecutor(Builder builder) {
		maxThreads = builder.maxThreads
		metrics = builder.metrics
		testClosures = new ArrayList<TestClosure>()
	}
}


