package com.kazurayam.ks.testclosure

import static org.junit.Assert.*

import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.Point as Point

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class StackingLayoutMetricsTest {

	StackingLayoutMetrics lm
	int NUM_OF_WINDOWS = 3

	@Before
	void setup() {
		lm = new StackingLayoutMetrics.Builder().windowDimension(new Dimension(1280, 1024)).build()
	}


	@Test
	void test_getWindowDimension() {
		assertTrue("width=${lm.getWindowDimension(NUM_OF_WINDOWS,0).width}, expected 1280",
				lm.getWindowDimension(NUM_OF_WINDOWS, 0).width == 1280)
		assertTrue("height=${lm.getWindowDimension(NUM_OF_WINDOWS, 0).height}, epected 1024",
				lm.getWindowDimension(NUM_OF_WINDOWS, 0).height == 1024)
	}

	@Test
	void testWindow0() {
		Point pos = lm.getWindowPosition(NUM_OF_WINDOWS, 0)
		assertTrue("pos.x=${pos.x}, expected to be equal to ${lm.getDisposition().width * 0}",
				pos.x == lm.getDisposition().width * 0)
		assertTrue("pos.y=${pos.y}, expected to be equal to ${lm.getDisposition().height * 0}",
				pos.y == lm.getDisposition().height * 0)
	}

	@Test
	void testWindow1() {
		Point pos = lm.getWindowPosition(NUM_OF_WINDOWS, 1)
		assertTrue("pos.x=${pos.x}, expected to be equal to ${lm.getDisposition().width * 1}",
				pos.x == lm.getDisposition().width * 1)
		assertTrue("pos.y=${pos.y}, expected to be equal to ${lm.getDisposition().height * 1}",
				pos.y== lm.getDisposition().height * 1)
	}

	@Test
	void testWindow2() {
		Point pos = lm.getWindowPosition(NUM_OF_WINDOWS, 2)
		assertTrue("pos.x=${pos.x}, expected to be equal to ${lm.getDisposition().width * 2}",
				pos.x == lm.getDisposition().width * 2)
		assertTrue("pos.y=${pos.y}, expected to be equal to ${lm.getDisposition().height * 2}",
				pos.y == lm.getDisposition().height * 2)
	}
}
