package com.kazurayam.ks.testclosure

import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.Point as Point

abstract class BrowserWindowsLayoutMetrics {

	abstract Point getWindowPosition(int capacity, int index)

	abstract Dimension getWindowDimension(int capacity, int index)

	protected static void validateIndex(int capacity, int index) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity must not be <= 0")
		}
		if (index < 0) {
			throw new IllegalArgumentException("index must not be < 0")
		}
		if (capacity <= index) {
			throw new IllegalArgumentException("index=${index} must not be larger than or equal to capacity=${capacity})")
		}
	}
}
