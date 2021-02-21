package com.kazurayam.ks.browserwindow

import static org.junit.Assert.*

import java.awt.Dimension
import java.awt.Point

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.browserwindow.TilingBrowserWindowManager

@RunWith(JUnit4.class)
class TilingBrowserWindowManagerTest {

	TilingBrowserWindowManager tm

	@Before
	void setup() {
		tm = new TilingBrowserWindowManager(4, new Dimension(1020, 820))
	}

	@Test
	void test_getNumberOfTiles() {
		assertTrue("expected 4 tiles", tm.getNumberOfTiles() == 4)
	}
	
	@Test
	void test_getNumberOfRows() {
		assertTrue("expected 2 rows but got ${tm.getNumberOfRows()}", tm.getNumberOfRows() == 2)
	}
	
	@Test
	void test_getNumberOfColumns() {
		assertTrue("expected 2 columns", tm.getNumberOfColumns() == 2)
	}
	
	@Test
	void test_getTileDimension() {
		Dimension tileDimension = tm.getTileDimension()
		assertTrue("expected width == 500", tileDimension.width == 500)
		assertTrue("expected height == 400", tileDimension.height == 400)
	}

	@Test
	void testTile0() {
		Point basePoint = tm.getBasePoint()
		Point loc = tm.getLocationOf(0)
		Dimension dim = tm.getDimensionOf(0)
		Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y)
		assertTrue("loc.x=${loc.x}, expected to be ${expectedLoc.x}", expectedLoc.x == loc.x)
		assertTrue("loc.y=${loc.y}, expected to be ${expectedLoc.y}", expectedLoc.y == loc.y)
		assertTrue("dim.width=${dim.width}, expected to be 500", 500 == dim.width)
		assertTrue("dim.height=${dim.height}, expected to be 400", 400 == dim.height)
	}

	@Test
	void testTile1() {
		Point basePoint = tm.getBasePoint()
		Point loc = tm.getLocationOf(1)
		Dimension dim = tm.getDimensionOf(1)
		Point expectedLoc = new Point((int)basePoint.x + (int)dim.width, (int)basePoint.y)
		assertTrue("loc.x=${loc.x}, expected to be ${expectedLoc.x}", expectedLoc.x == loc.x)
		assertTrue("loc.y=${loc.y}, expected to be ${expectedLoc.y}", expectedLoc.y == loc.y)
		assertTrue("dim.width=${dim.width}, expected to be 500", 500 == dim.width)
		assertTrue("dim.height=${dim.height}, expected to be 400", 400 == dim.height)
	}

	@Test
	void testTile2() {
		Point basePoint = tm.getBasePoint()
		Point loc = tm.getLocationOf(2)
		Dimension dim = tm.getDimensionOf(2)
		Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y + (int)dim.height)
		assertTrue("loc.x=${loc.x}, expected to be ${expectedLoc.x}", expectedLoc.x == loc.x)
		assertTrue("loc.y=${loc.y}, expected to be ${expectedLoc.y}", expectedLoc.y == loc.y)
		assertTrue("dim.width=${dim.width}, expected to be 500", 500 == dim.width)
		assertTrue("dim.height=${dim.height}, expected to be 400", 400 == dim.height)
	}

}