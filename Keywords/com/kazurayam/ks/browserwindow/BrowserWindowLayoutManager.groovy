package com.kazurayam.ks.browserwindow

import java.awt.Point
import java.awt.Dimension

interface BrowserWindowLayoutManager {

	Point getPosition(int tileIndex)

	Dimension getDimension(int tileIndex)
	
}
