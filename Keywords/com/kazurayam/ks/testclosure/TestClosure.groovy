package com.kazurayam.ks.testclosure

import java.time.LocalDateTime
import java.util.concurrent.Callable

import org.openqa.selenium.chrome.ChromeDriver

/**
 * 
 * @author kazurayam
 */
public class TestClosure implements Callable<TestClosureResult> {

	private final Closure closure
	private final List<Object> parameters

	private ChromeDriver driver

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
		validateClosureParameters(closure)
		this.closure = closure
		this.parameters = parameters
	}

	public void setDriver(ChromeDriver driver) {
		Objects.requireNonNull(driver)
		this.driver = driver
	}

	@Override
	public TestClosureResult call() throws Exception {
		Objects.requireNonNull(driver)
		//
		List<Object> args = new ArrayList<Object>()
		args.add(driver)
		args.addAll(parameters)
		LocalDateTime startAt = LocalDateTime.now()
		//
		closure.call(args)
		//
		TestClosureResult result = new TestClosureResult(closure.getClass().getName(), LocalDateTime.now())
		result.setStopAt(LocalDateTime.now())
		result.setMessage("done")
		return result
	}

	/**
	 * 
	 * @param closure
	 */
	private void validateClosureParameters(Closure closure) {
		Objects.requireNonNull(closure)
		def parameterTypes = closure.getParameterTypes()
		if (parameterTypes.length < 1) {
			throw new IllegalArgumentException("TestClosure requires a Closure with 1 or more parameters. " +
			"{ WebDriver driver[, xxx...] -> ... }")
		}
		if (parameterTypes[0] != ChromeDriver.class) {
			throw new IllegalArgumentException("TestClosure requires the 1st Closure parameter to be ${ChromeDriver.class.toString()} type")
		}
	}
}
