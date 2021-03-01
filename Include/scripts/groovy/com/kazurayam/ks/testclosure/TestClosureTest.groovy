package com.kazurayam.ks.testclosure

import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation

import static org.junit.Assert.*
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class TestClosureTest {

	@Before
	void setup() {}

	/**
	 * No Exception expected
	 */
	@Test
	void test_closure_pass() {
		Closure cl = { Point position, Dimension dimension ->
			print "${position.toString()},${dimension.toString()}"
		}
		TestClosure tc = new TestClosure(cl, [])
		tc.setPosition(new Point(0, 0))
		tc.setDimension(new Dimension(1024, 768))
		tc.call()
	}

	@Test
	void test_closure_must_have_2_or_more_parameters() {
		try {
			Closure cl = { name -> println "Hello, ${name}!" }
			TestClosure tcl = new TestClosure(cl, [])
			fail("should fail, but did jot")
		} catch (IllegalArgumentException e) {
			;
		}
	}

	@Test
	void test_closure_1st_parameter_type() {
		try {
			Closure cl = { String arg0, String arg1 ->
				print "${arg0},${arg1}"
			}
			TestClosure tc = new TestClosure(cl, [])
			fail("should fail, but did not")
		} catch (IllegalArgumentException e) {
			assertTrue("msg=" + e.getMessage(), e.getMessage().contains("Point"))
		}
	}

	@Test
	void test_closure_2nd_parameter_type() {
		try {
			Closure cl = { Point position, String arg1 ->
				print "${position.toString()},${arg1}"
			}
			TestClosure tc = new TestClosure(cl, [])
			fail("should fail, but did not")
		} catch (IllegalArgumentException e) {
			assertTrue("msg=" + e.getMessage(), e.getMessage().contains("Dimension"))
		}
	}
}
