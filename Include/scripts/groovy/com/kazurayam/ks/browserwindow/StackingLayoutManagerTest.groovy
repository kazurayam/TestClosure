package com.kazurayam.ks.browserwindow

import static org.junit.Assert.*

import java.awt.Dimension
import java.awt.Point

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class StackingLayoutManagerTest {

	StackingLayoutManager lm

	@Before
	void setup() {
		lm = new StackingLayoutManager.Builder(4).windowDimension(new Dimension(1280, 1024)).build()
	}

	@Test
	void test_getNumberOfWindows() {
		assertTrue("expected 4 windows", lm.getNumberOfWindows() == 4)
	}

	@Test
	void test_getWindowDimension() {
		assertTrue("width=${lm.getWindowDimension().width}, expected 1280",
				lm.getWindowDimension().width == 1280)
		assertTrue("height=${lm.getWindowDimension().height}, epected 1024",
				lm.getWindowDimension().height == 1024)
	}

	@Test
	void testWindow0() {
		Point pos = lm.getPosition(0)
		assertTrue("pos.x=${pos.x}, expected to be equal to ${lm.getDisposition().width * 0}",
				pos.x == lm.getDisposition().width * 0)
		assertTrue("pos.y=${pos.y}, expected to be equal to ${lm.getDisposition().height * 0}",
				pos.y == lm.getDisposition().height * 0)
	}

	@Test
	void testWindow1() {
		Point pos = lm.getPosition(1)
		assertTrue("pos.x=${pos.x}, expected to be equal to ${lm.getDisposition().width * 1}",
				pos.x == lm.getDisposition().width * 1)
		assertTrue("pos.y=${pos.y}, expected to be equal to ${lm.getDisposition().height * 1}",
				pos.y== lm.getDisposition().height * 1)
	}

	@Test
	void testWindow2() {
		Point pos = lm.getPosition(2)
		assertTrue("pos.x=${pos.x}, expected to be equal to ${lm.getDisposition().width * 2}",
				pos.x == lm.getDisposition().width * 2)
		assertTrue("pos.y=${pos.y}, expected to be equal to ${lm.getDisposition().height * 2}",
				pos.y == lm.getDisposition().height * 2)
	}
}
