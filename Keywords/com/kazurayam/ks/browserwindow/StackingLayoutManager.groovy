package com.kazurayam.ks.browserwindow

import java.awt.Point
import java.awt.Dimension

public class StackingLayoutManager implements BrowserWindowsLayoutManager {

	private final int numberOfWindows
	private final Dimension windowDimension
	private final Dimension disposition = new Dimension(100, 80)

	StackingLayoutManager(int numberOfWindows) {
		this(numberOfWindows, new Dimension(1024, 768))
	}
	StackingLayoutManager(int numberOfWindows, Dimension windowDimension) {
		this.numberOfWindows = numberOfWindows
		this.windowDimension = windowDimension
	}

	int getNumberOfWindows() {
		return numberOfWindows
	}

	Dimension getWindowDimension() {
		return windowDimension
	}

	Dimension getDisposition() {
		return disposition
	}

	@Override
	Point getPosition(int windowIndex) {
		validateWindowIndex(windowIndex)
		int x = disposition.width * windowIndex
		int y = disposition.height * windowIndex
		return new Point(x, y)
	}

	@Override
	Dimension getDimension(int tileIndex) {
		return windowDimension
	}

	private validateWindowIndex(int windowIndex) {
		if (windowIndex < 0) {
			throw new IllegalArgumentException("windowIndex=${windowIndex}, must be >= 0")
		}
		if (windowIndex >= this.numberOfWindows) {
			throw new IllegalArgumentException("windowIndex=${windowIndex}, must be less than numberOfWindows")
		}
	}
}
