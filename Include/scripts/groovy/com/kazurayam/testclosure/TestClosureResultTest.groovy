package com.kazurayam.testclosure

import static org.junit.Assert.*

import java.time.LocalDateTime

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class TestClosureResultTest {

	@Test
	void test_equality() {
		String name = "test_equality"
		LocalDateTime startAt = LocalDateTime.now()
		Thread.sleep(100)
		LocalDateTime stopAt = LocalDateTime.now()
		String message = "I am fine"
		TestClosureResult r1 = new TestClosureResult(name, startAt).setStopAt(stopAt).setMessage(message)
		assert r1 == r1
		assertEquals(name, r1.getName())
		assertEquals(startAt, r1.getStartAt())
		assertEquals(stopAt, r1.getStopAt())
		assertEquals(message, r1.getMessage())
		TestClosureResult r2 = new TestClosureResult(name, startAt).setStopAt(stopAt).setMessage(message)
		assert r2 == r1
	}

	@Test
	void test_periodMillis() {
		String name = "test_periodMillis"
		LocalDateTime startAt = LocalDateTime.now()
		Thread.sleep(100)
		LocalDateTime stopAt = LocalDateTime.now()
		String message = "done"
		TestClosureResult r1 = new TestClosureResult(name, startAt).setStopAt(stopAt).setMessage(message)
		long period = r1.periodMilli()
		assertTrue("period=${period} is expected to be > 100", period > 100)
		assertTrue("period=${period} is expected to be < 1000", period < 1000)
	}
}
