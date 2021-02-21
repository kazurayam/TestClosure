package com.kazurayam.ks.browserwindow

import java.awt.Point
import java.awt.Dimension

interface BrowserWindowsLayoutManager {

	Point getPosition(int tileIndex)

	Dimension getDimension(int tileIndex)
}
