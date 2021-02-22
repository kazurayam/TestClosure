package com.kazurayam.ks.browserwindow

import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.Point as Point

import java.awt.Toolkit

public class TilingLayoutManager extends BrowserWindowsLayoutManager {

	protected final Dimension virtualScreenSize
	protected final Point basePoint

	Dimension getVirtualScreenSize() {
		return virtualScreenSize
	}

	Point getBasePoint() {
		return basePoint
	}

	@Override
	Dimension getWindowDimension(int capacity, int index) {
		validateIndex(capacity, index)
		if (capacity == 1) {
			return virtualScreenSize
		} else {
			// Tiles in 2 columns
			int width = Math.floor(virtualScreenSize.width / 2)
			int rows = Math.ceil(capacity / 2)
			int height = Math.floor(virtualScreenSize.height / rows )
			return new Dimension(width, height)
		}
	}

	@Override
	Point getWindowPosition(int capacity, int index) {
		validateIndex(capacity, index)
		int x = basePoint.x + (index % 2) * this.getWindowDimension(capacity, index).width
		int y = basePoint.y + Math.floor(index / 2) * this.getWindowDimension(capacity, index).height
		return new Point(x, y)
	}


	/**
	 * Builder by Effective Java
	 */
	public static class Builder {
		// Required parameters

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

		TilingLayoutManager build() {
			return new TilingLayoutManager(this)
		}
	}

	private TilingLayoutManager(Builder builder) {
		virtualScreenSize = new Dimension(
				builder.physicalScreenSize.width - builder.basePoint.x * 2,
				builder.physicalScreenSize.height - builder.basePoint.y * 2
				)
		basePoint = builder.basePoint
	}
}