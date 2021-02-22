package com.kazurayam.ks.browserwindow

import java.awt.Point
import java.awt.Dimension

interface BrowserWindowsLayoutManager {

	Point getWindowPosition(int range, int index)

	Dimension getWindowDimension(int range, int index)
	
	
}
