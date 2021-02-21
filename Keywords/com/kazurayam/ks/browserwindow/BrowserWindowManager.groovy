package com.kazurayam.ks.browserwindow

import java.awt.Point
import java.awt.Dimension

interface BrowserWindowManager {
	
	Point getLocationOf(int tileIndex)
	
	Dimension getDimensionOf(int tileIndex)
	
}
