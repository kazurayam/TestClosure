package com.kazurayam.ks.testclosure

import com.kazurayam.ks.windowlayout.WindowLayoutMetrics
import com.kazurayam.ks.windowlayout.WindowLocation

import static org.junit.Assert.*

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
		Closure cl = { WindowLayoutMetrics metrics, WindowLocation location ->
			print "${metrics.toString()},${location.toString()}"
		}
		TestClosure tc = new TestClosure(cl, [])
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
			assertTrue("msg=" + e.getMessage(), e.getMessage().contains("WindowLayoutMetrics"))
		}
	}

	@Test
	void test_closure_2nd_parameter_type() {
		try {
			Closure cl = { WindowLayoutMetrics metrics, String arg1 ->
				print "${metrics.toString()},${arg1}"
			}
			TestClosure tc = new TestClosure(cl, [])
			fail("should fail, but did not")
		} catch (IllegalArgumentException e) {
			assertTrue("msg=" + e.getMessage(), e.getMessage().contains("WindowLocation"))
		}
	}
}
