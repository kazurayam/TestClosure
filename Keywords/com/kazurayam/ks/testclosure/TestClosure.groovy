package com.kazurayam.ks.testclosure

import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver

import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * 
 * @author kazurayam
 */
public class TestClosure implements Callable<String> {

	private WindowLayoutMetrics layoutMetrics
	private WindowLocation windowLocation
	private Closure closure
	private List<Object> parameters

	/*
	 * 
	 */
	private static final Closure manageLayout = { WindowLayoutMetrics layout, WindowLocation windowLocation ->
		WebDriver driver = DriverFactory.getWebDriver()
		// move the browser window to this position (x,y)
		Point pos = layout.getWindowPosition(windowLocation)
		driver.manage().window().setPosition(pos)
		// resize the browser window to this dimension (width, height)
		Dimension dim = layout.getWindowDimension(windowLocation)
		driver.manage().window().setSize(dim)
	}

	/**
	 * Constructor
	 * 
	 * @param capacity
	 * @param index
	 * @param closure
	 */
	public TestClosure(WindowLayoutMetrics layoutMetrics, WindowLocation windowLocation, Closure closure, List<Object> parameters) {
		Objects.requireNonNull(layoutMetrics)
		Objects.requireNonNull(closure)
		Objects.requireNonNull(parameters)
		this.layoutMetrics = layoutMetrics
		this.windowLocation = windowLocation
		this.closure = closure
		this.parameters = parameters
	}

	@Override
	public String call() throws Exception {
		TimeUnit.MILLISECONDS.sleep(300)
		/*
		 * modify the built-in 'WebUI.openBrowser()' keyword so that
		 * the browser window is moved to an point (x, y) and
		 * the browser window is resized to have width and height
		 * both are derived by the layoutMetrics
		 */
		WebUI.metaClass.'static'.invokeMethod = { String name, args ->
			def result
			try {
				result = delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
			} catch (Exception e) {
				System.out.println("Handling exception for method \'${name}\'")
			}
			// do magic soon after WebUI.openBrowser() is done
			if (name == 'openBrowser') {
				manageLayout.call(owner.layoutMetrics, owner.windowLocation)
			}
			return result
		}
		closure.call(parameters)
		return "Task' execution"
	}
}
