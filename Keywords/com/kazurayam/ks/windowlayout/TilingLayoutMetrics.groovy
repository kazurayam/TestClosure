package com.kazurayam.ks.windowlayout

import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.Point

import java.awt.Toolkit

public class TilingLayoutMetrics extends WindowLayoutMetrics {

	protected final Dimension virtualScreenSize
	protected final Point basePoint

	Dimension getVirtualScreenSize() {
		return virtualScreenSize
	}

	Point getBasePoint() {
		return basePoint
	}

	@Override
	Dimension getWindowDimension(WindowLocation windowLocation) {
		if (windowLocation.size == 1) {
			return virtualScreenSize
		} else {
			// Tiles in 2 columns
			int width = Math.floor(virtualScreenSize.width / 2)
			int rows = Math.ceil(windowLocation.size / 2)
			int height = Math.floor(virtualScreenSize.height / rows )
			return new Dimension(width, height)
		}
	}

	@Override
	Point getWindowPosition(WindowLocation windowLocation) {
		int x = basePoint.x + (windowLocation.index % 2) * this.getWindowDimension(windowLocation).width
		int y = basePoint.y + Math.floor(windowLocation.index / 2) * this.getWindowDimension(windowLocation).height
		return new Point(x, y)
	}


	/**
	 * Builder pattern by "Effective Java"
	 */
	public static class Builder {
		// Required parameters - none

		// Optional parameters - initialized to default values
		private java.awt.Dimension ss = Toolkit.getDefaultToolkit().getScreenSize()
		private Dimension physicalScreenSize = new Dimension((int)ss.width, (int)ss.height)
		private Point basePoint = new Point(10, 10)

		public Builder() {}

		public Builder physicalScreenSize(Dimension physicalScreenSize) {
			this.physicalScreenSize = physicalScreenSize
			return this
		}

		public Builder basePoint(Point basePoint) {
			this.basePoint = basePoint
			return this
		}

		TilingLayoutMetrics build() {
			return new TilingLayoutMetrics(this)
		}
	}

	private TilingLayoutMetrics(Builder builder) {
		virtualScreenSize = new Dimension(
				builder.physicalScreenSize.width - builder.basePoint.x * 2,
				builder.physicalScreenSize.height - builder.basePoint.y * 2
				)
		basePoint = builder.basePoint
	}
}