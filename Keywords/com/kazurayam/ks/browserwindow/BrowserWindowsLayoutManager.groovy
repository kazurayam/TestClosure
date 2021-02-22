package com.kazurayam.ks.browserwindow

import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.Point as Point

abstract class BrowserWindowsLayoutManager {

	abstract Point getWindowPosition(int range, int index)

	abstract Dimension getWindowDimension(int range, int index)
	
	protected void validateIndex(int numOfWindows, int index) {
		if (numOfWindows <= 0) {
			throw new IllegalArgumentException("numOfWaindows must not be <= 0")
		}
		if (index < 0) {
			throw new IllegalArgumentException("index must not be < 0")
		}
		if (numOfWindows <= index) {
			throw new IllegalArgumentException("index=${index} must not be larger than or equal to numberOfTWindows=${numOfWindows})")
		}
	}
}
