package com.kazurayam.ks.testclosure

import java.time.LocalDateTime
import java.util.concurrent.Callable

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

/**
 * 
 * @author kazurayam
 */
public class TestClosure implements Callable<TestClosureResult> {

	private final Closure closure
	private final List<Object> parameters

	private Point position
	private Dimension dimension

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
		this.position = new Point(0, 0)
		this.dimension = new Dimension(1280, 760)
	}

	public void setPosition(Point position) {
		Objects.requireNonNull(position)
		this.position = position
	}

	public void setDimension(Dimension dimension) {
		Objects.requireNonNull(dimension)
		this.dimension = dimension
	}

	@Override
	public TestClosureResult call() throws Exception {
		Objects.requireNonNull(position)
		Objects.requireNonNull(dimension)
		//
		List<Object> args = new ArrayList<Object>()
		args.add(position)
		args.add(dimension)
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
		if (parameterTypes.length < 2) {
			throw new IllegalArgumentException("TestClosure requires a Closure with 2 or more parameters. " +
			"{ Point position, Dimension dimension [, xxx...] -> ... }")
		}
		if (parameterTypes[0] != Point.class) {
			throw new IllegalArgumentException("TestClosure requires the 1st Closure parameter to be ${Point.class.toString()} type")
		}
		if (parameterTypes[1] != Dimension.class) {
			throw new IllegalArgumentException("TestClosure requires the 2nd Closure parameter to be ${Dimension.class.toString()} type")
		}
	}
}
