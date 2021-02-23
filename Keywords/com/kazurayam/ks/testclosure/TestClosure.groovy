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

	private WindowLayoutMetrics metrics
	private WindowLocation location
	private Closure closure
	private List<Object> parameters

	/**
	 * Constructor
	 * 
	 * @param capacity
	 * @param index
	 * @param closure
	 */
	public TestClosure(WindowLayoutMetrics metrics, WindowLocation location, Closure closure, List<Object> parameters) {
		Objects.requireNonNull(metrics)
		Objects.requireNonNull(location)
		Objects.requireNonNull(closure)
		Objects.requireNonNull(parameters)
		this.metrics = metrics
		this.location = location
		this.closure = closure
		this.parameters = parameters
	}

	@Override
	public String call() throws Exception {
		def args = [ metrics, location ]
		args.addAll(parameters)
		closure.call(args)
		return "done"
	}
}
