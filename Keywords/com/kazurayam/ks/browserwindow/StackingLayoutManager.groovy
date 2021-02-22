package com.kazurayam.ks.browserwindow

import java.awt.Point
import java.awt.Dimension

public class StackingLayoutManager implements BrowserWindowsLayoutManager {

	private final int numberOfWindows
	private final Dimension windowDimension
	private final Dimension disposition

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
	
	/**
	 * Builder by Effective Java
	 */
	public static class Builder {
		// Required parameters
		private final int numberOfWindows
		
		// Optional parameters - initialized to default values
		private Dimension windowDimension = new Dimension(1024, 768)
		private Dimension disposition = new Dimension(100, 80)
		
		public Builder(int numberOfWindows) {
			this.numberOfWindows = numberOfWindows
		}
		
		public Builder windowDimension(Dimension windowDimension) {
			this.windowDimension = windowDimension
			return this
		}
		
		public Builder disposition(Dimension disposition) {
			this.disposition = disposition
			return this
		}
		
		StackingLayoutManager build() {
			return new StackingLayoutManager(this)
		}
	}
	
	StackingLayoutManager(int numberOfWindows, Dimension windowDimension) {
		this.numberOfWindows = numberOfWindows
		this.windowDimension = windowDimension
	}
	
	private StackingLayoutManager(Builder builder) {
		numberOfWindows = builder.numberOfWindows
		windowDimension = builder.windowDimension
		disposition     = builder.disposition
	}

}
