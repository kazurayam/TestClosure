package com.kazurayam.ks.browserwindow

import static org.junit.Assert.*

import java.awt.Dimension
import java.awt.Point

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.browserwindow.TilingLayoutManager

@RunWith(JUnit4.class)
class TilingLayoutManagerTest {

	TilingLayoutManager lm

	@Before
	void setup() {
		lm = new TilingLayoutManager(4, new Dimension(1020, 820))
	}

	@Test
	void test_getNumberOfTiles() {
		assertTrue("expected 4 tiles", lm.getNumberOfTiles() == 4)
	}

	@Test
	void test_getNumberOfRows() {
		assertTrue("expected 2 rows but got ${lm.getNumberOfRows()}", lm.getNumberOfRows() == 2)
	}

	@Test
	void test_getNumberOfColumns() {
		assertTrue("expected 2 columns", lm.getNumberOfColumns() == 2)
	}

	@Test
	void test_getTileDimension() {
		Dimension tileDimension = lm.getTileDimension()
		assertTrue("expected width == 500", tileDimension.width == 500)
		assertTrue("expected height == 400", tileDimension.height == 400)
	}

	@Test
	void testTile0() {
		Point basePoint = lm.getBasePoint()
		Point loc = lm.getPosition(0)
		Dimension dim = lm.getDimension(0)
		Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y)
		assertTrue("loc.x=${loc.x}, expected to be ${expectedLoc.x}", expectedLoc.x == loc.x)
		assertTrue("loc.y=${loc.y}, expected to be ${expectedLoc.y}", expectedLoc.y == loc.y)
		assertTrue("dim.width=${dim.width}, expected to be 500", 500 == dim.width)
		assertTrue("dim.height=${dim.height}, expected to be 400", 400 == dim.height)
	}

	@Test
	void testTile1() {
		Point basePoint = lm.getBasePoint()
		Point loc = lm.getPosition(1)
		Dimension dim = lm.getDimension(1)
		Point expectedLoc = new Point((int)basePoint.x + (int)dim.width, (int)basePoint.y)
		assertTrue("loc.x=${loc.x}, expected to be ${expectedLoc.x}", expectedLoc.x == loc.x)
		assertTrue("loc.y=${loc.y}, expected to be ${expectedLoc.y}", expectedLoc.y == loc.y)
		assertTrue("dim.width=${dim.width}, expected to be 500", 500 == dim.width)
		assertTrue("dim.height=${dim.height}, expected to be 400", 400 == dim.height)
	}

	@Test
	void testTile2() {
		Point basePoint = lm.getBasePoint()
		Point loc = lm.getPosition(2)
		Dimension dim = lm.getDimension(2)
		Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y + (int)dim.height)
		assertTrue("loc.x=${loc.x}, expected to be ${expectedLoc.x}", expectedLoc.x == loc.x)
		assertTrue("loc.y=${loc.y}, expected to be ${expectedLoc.y}", expectedLoc.y == loc.y)
		assertTrue("dim.width=${dim.width}, expected to be 500", 500 == dim.width)
		assertTrue("dim.height=${dim.height}, expected to be 400", 400 == dim.height)
	}
}