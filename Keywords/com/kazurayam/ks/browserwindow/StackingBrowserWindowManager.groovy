package com.kazurayam.ks.browserwindow

import java.awt.Point
import java.awt.Dimension

public class StackingBrowserWindowManager implements BrowserWindowManager {

	StackingBrowserWindowManager() {}
	
	@Override
	Point getLocationOf(int tileIndex) {
		return new Point(10, 10)
	}

	@Override
	Dimension getDimensionOf(int tileIndex) {
		int width = 800
		int height = 600
		return new Dimension(width, height)
	}
}
