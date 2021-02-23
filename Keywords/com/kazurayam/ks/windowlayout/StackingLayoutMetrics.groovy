package com.kazurayam.ks.windowlayout

import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.Point

import com.kazurayam.ks.windowlayout.WindowLocation


public class StackingLayoutMetrics extends WindowLayoutMetrics {

	private final Dimension windowDimension
	private final Dimension disposition

	Dimension getDisposition() {
		return disposition
	}

	@Override
	Point getWindowPosition(WindowLocation windowLocation) {
		int x = disposition.width * windowLocation.index
		int y = disposition.height * windowLocation.index
		return new Point(x, y)
	}

	@Override
	Dimension getWindowDimension(WindowLocation windowLocation) {
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

		StackingLayoutMetrics build() {
			return new StackingLayoutMetrics(this)
		}
	}


	private StackingLayoutMetrics(Builder builder) {
		windowDimension = builder.windowDimension
		disposition     = builder.disposition
	}

}
