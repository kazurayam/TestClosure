package com.kazurayam.ks

import static org.junit.Assert.*

import java.awt.Dimension
import java.awt.Point

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
class TilesManagerTest {

	TilesManager tm

	@Before
	void setup() {
		tm = new TilesManager(4, new Dimension(1000, 800))
	}

	@Test
	void testTile0() {
		Point loc = tm.getLocationOf(0)
		assertTrue("loc.x should be 0", 0 == loc.x)
		assertTrue("loc.y should be 0", 0 == loc.y)
		Dimension dim = tm.getDimensionOf(0)
		assertTrue("dim.width should be 500", 500 == dim.width)
		assertTrue("dim.height should be 400", 400 == dim.height)
	}

	@Test
	void testTile1() {
		Point loc = tm.getLocationOf(1)
		assertTrue(500 == loc.x)
		assertTrue(0 == loc.y)
		Dimension dim = tm.getDimensionOf(1)
		assertTrue(500 == dim.width)
		assertTrue(400 == dim.height)
	}

	@Test
	void testTile2() {
		Point loc = tm.getLocationOf(2)
		assertTrue(0 == loc.x)
		assertTrue(400 == loc.y)
		Dimension dim = tm.getDimensionOf(2)
		assertTrue(500 == dim.width)
		assertTrue(400 == dim.height)
	}

	@Test
	void testTile3() {
		Point loc = tm.getLocationOf(3)
		assertTrue(500 == loc.x)
		assertTrue(400 == loc.y)
		Dimension dim = tm.getDimensionOf(3)
		assertTrue(500 == dim.width)
		assertTrue(400 == dim.height)
	}
}