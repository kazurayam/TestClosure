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
		assertTrue(10 == loc.x)
		assertTrue(10 == loc.y)
		Dimension dim = tm.getDimensionOf(0)
		assertTrue(800 == dim.width)
		assertTrue(600 == dim.height)
	}
}