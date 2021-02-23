package com.kazurayam.ks.testclosure

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.WebDriver
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * 
 * @author kazurayam
 */
public class TestClosuresExecutor {

	private static final int MAX_THREADS = 6

	private final BrowserWindowsLayoutMetrics layoutManager
	private int capacity
	private List<Callable<String>> callableTasks

	def manageLayout = { BrowserWindowsLayoutMetrics layout, int capacity, int index ->
		WebDriver driver = DriverFactory.getWebDriver()
		// move the browser window to this position (x,y)
		Point pos = layout.getWindowPosition(capacity, index)
		driver.manage().window().setPosition(pos)
		// resize the browser window to thiis dimension (width, height)
		Dimension dim = layout.getWindowDimension(capacity, index)
		driver.manage().window().setSize(dim)
	}

	public TestClosuresExecutor(BrowserWindowsLayoutMetrics layoutManager) {
		this.layoutManager = layoutManager
		this.callableTasks = new ArrayList<Callable<String>>()
	}

	public void addAllClosures(List<Closure> closures) {
		capacity = (closures.size() > MAX_THREADS) ? MAX_THREADS : closures.size()
		for (int i = 0; i < closures.size(); i++) {
			this.addClosure(closures.get(i), i)
		}
	}

	private void addClosure(Closure closure, int index) {
		println "given index is ${index}"
		Callable<String> callableTask = {
			TimeUnit.MILLISECONDS.sleep(300)
			//
			WebUI.metaClass.'static'.invokeMethod = { String name, args ->
				def result
				try {
					result = delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
				} catch (Exception e) {
					System.out.println("Handling exception for method \'$name\'")
				}
				// modify WebUI.openBrowser() method
				if (name == "openBrowser") {
					// move and resize the browser window
					println "passed capacity is ${capacity}, passed index is ${index}"
					manageLayout.call(layoutManager, capacity, index)
				}
				return result
			}
			//
			closure.call()
			return "Task's execution"
		}
		this.callableTasks.add(callableTask)
	}

	void execute() {
		int size = callableTasks.size()
		if (size < 1) {
			throw new IllegalStateException("should add one or more Callable objects")
		}

		// create Thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(
				(size > MAX_THREADS) ?  MAX_THREADS : size)

		List<Future<String>> futures = executorService.invokeAll(callableTasks)

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

