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

import com.kazurayam.ks.windowlayout.TilingLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * 
 * @author kazurayam
 */
public class TestClosureCollectionExecutor {

	private static final int MAX_THREADS = 8

	private final WindowLayoutMetrics metrics
	private int capacity
	private List<TestClosure> testClosures

	public TestClosureCollectionExecutor() {
		this(new TilingLayoutMetrics.Builder().build())
	}

	public TestClosureCollectionExecutor(WindowLayoutMetrics metrics) {
		this.metrics = metrics
		this.testClosures = new ArrayList<TestClosure>()
	}

	public void addAllClosures(List<TestClosure> tclosures) {
		capacity = (tclosures.size() > MAX_THREADS) ? MAX_THREADS : tclosures.size()
		for (int i = 0; i < tclosures.size(); i++) {
			this.addClosure(tclosures.get(i), i)
		}
	}

	private void addClosure(TestClosure tclosure, int index) {
		Objects.requireNonNull(tclosure)
		tclosure.setWindowLayoutMetrics(metrics)
		tclosure.setWindowLocation(new WindowLocation(capacity, index))
		this.testClosures.add(tclosure)
	}

	void execute() {
		int size = testClosures.size()
		if (size < 1) {
			throw new IllegalStateException("should add one or more TestClosure objects")
		}

		// create Thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(
				(size > MAX_THREADS) ?  MAX_THREADS : size)

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
}

