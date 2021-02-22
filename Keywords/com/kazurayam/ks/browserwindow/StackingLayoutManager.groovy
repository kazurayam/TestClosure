package com.kazurayam.ks.browserwindow

import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.Point as Point


public class StackingLayoutManager extends BrowserWindowsLayoutManager {

	private final Dimension windowDimension
	private final Dimension disposition

	Dimension getDisposition() {
		return disposition
	}

	@Override
	Point getWindowPosition(int capacity, int index) {
		validateIndex(capacity, index)
		int x = disposition.width * index
		int y = disposition.height * index
		return new Point(x, y)
	}

	@Override
	Dimension getWindowDimension(int capacity, int index) {
		return windowDimension
	}

	/**
	 * Builder by Effective Java
	 */
	public static class Builder {
		// Required parameters

		// Optional parameters - initialized to default values
		private Dimension windowDimension = new Dimension(1024, 768)
		private Dimension disposition = new Dimension(100, 80)

		public Builder() {
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


	private StackingLayoutManager(Builder builder) {
		windowDimension = builder.windowDimension
		disposition     = builder.disposition
	}

}
