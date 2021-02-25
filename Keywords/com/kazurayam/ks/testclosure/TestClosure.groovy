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

	private final Closure closure
	private final List<Object> parameters

	private WindowLayoutMetrics metrics
	private WindowLocation location

	/**
	 * Constructor
	 * 
	 * @param capacity
	 * @param index
	 * @param closure
	 */
	public TestClosure(Closure closure, List<Object> parameters) {
		Objects.requireNonNull(closure)
		Objects.requireNonNull(parameters)
		assert parameters instanceof List
		this.closure = closure
		this.parameters = parameters
	}

	public void setWindowLayoutMetrics(WindowLayoutMetrics metrics) {
		Objects.requireNonNull(metrics)
		this.metrics = metrics
	}

	public void setWindowLocation(WindowLocation location) {
		Objects.requireNonNull(location)
		this.location = location
	}

	@Override
	public String call() throws Exception {
		Objects.requireNonNull(metrics)
		Objects.requireNonNull(location)
		//
		List<Object> args = new ArrayList<Object>()
		args.add(metrics)
		args.add(location)
		args.addAll(parameters)
		closure.call(args)
		return "done"
	}
}
