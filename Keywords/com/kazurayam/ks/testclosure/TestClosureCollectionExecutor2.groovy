package com.kazurayam.ks.testclosure

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver

import com.kazurayam.browserwindowlayout.CellLayoutMetrics
import com.kazurayam.browserwindowlayout.TilingCellLayoutMetrics

public class TestClosureCollectionExecutor2 {

	private final CellLayoutMetrics metrics
	private final List<Closure> loadedTestClosures
	private int capacity

	private final WebDriversContainer webDriversContainer

	private TestClosureCollectionExecutor2(Builder builder) {
		this.metrics = builder.metrics
		this.loadedTestClosures = new ArrayList<Closure>()
		this.webDriversContainer = builder.webDriversContainer
	}

	public int getNumThreads() {
		return webDriversContainer.size()
	}

	public int size() {
		return loadedTestClosures.size()
	}

	public void addTestClosures(List<TestClosure> tclosures) {
		capacity = (tclosures.size() > getNumThreads()) ? getNumThreads() : tclosures.size()
		for (int i = 0; i < tclosures.size(); i++) {
			WebDriver driver = webDriversContainer.get(tclosures.size() % webDriversContainer.size())
			this.loadTestClosure(tclosures.get(i), driver)
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
	private void loadTestClosure(TestClosure tc, WebDriver driver) {
		Objects.requireNonNull(tc)
		Objects.requireNonNull(driver)
		int index = resolveIndex(this.loadedTestClosures.size())
		// the position (x,y) to which browser window should be moved to
		Point position = metrics.getCellPosition(index)
		// the size (width, height) to which browser window should be resized to
		Dimension dimension = metrics.getCellDimension(index)
		//
		Closure cls = {
			// a browser window is already opened
			// move the browser window to a good position (x,y)
			driver.manage().window().setPosition(position)
			// resize the browser window to a good dimension (width, height)
			driver.manage().window().setSize(dimension)
			// pass the WebDriver instance to this TestClosure
			tc.setDriver(driver)
			// execute the TestClosure
			TestClosureResult result = tc.call()
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
		}
	}


	/**
	 * Builder pattern by "Effective Java"
	 */
	public static class Builder {
		// Required parameters - none
		// Optional parameters - initialized to default values
		private WebDriversContainer webDriversContainer
		private CellLayoutMetrics metrics
		private List<TestClosure> testClosures = new ArrayList<TestClosure>()
		private List<String> userProfiles = []
		Builder(WebDriversContainer webDriversContainer) {
			Objects.requireNonNull(webDriversContainer)
			if (webDriversContainer.size() == 0) {
				throw new IllegalArgumentException("webDriversContainer.size() returned 0")
			}
			this.webDriversContainer = webDriversContainer
		}
		Builder windowLayoutMetrics(CellLayoutMetrics metrics) {
			this.metrics = metrics
			return this
		}
		TestClosureCollectionExecutor2 build() {
			if (this.metrics == null) {
				metrics = new TilingCellLayoutMetrics.Builder(webDriversContainer.size()).build()
			}
			return new TestClosureCollectionExecutor2(this)
		}
	}
}
