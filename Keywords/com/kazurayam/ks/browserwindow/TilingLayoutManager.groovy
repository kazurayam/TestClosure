package com.kazurayam.ks.browserwindow

import java.awt.Dimension
import java.awt.Point
import java.awt.Toolkit

public class TilingLayoutManager extends LayoutManagerBase implements BrowserWindowsLayoutManager {

	protected final Dimension virtualScreenSize
	protected final Point basePoint

	Dimension getVirtualScreenSize() {
		return virtualScreenSize
	}

	Point getBasePoint() {
		return basePoint
	}

	@Override
	Dimension getWindowDimension(int numOfWindows, int index) {
		validateIndex(numOfWindows, index)
		if (numOfWindows == 1) {
			return virtualScreenSize
		} else {
			// Tiles in 2 columns
			int numberOfRows = Math.ceil(numOfWindows / 2)
			int numberOfColumns = 2
			int width = Math.floor(virtualScreenSize.width / 2)
			int rows = Math.ceil(numOfWindows / 2)
			int height = Math.floor(virtualScreenSize.height / rows )
			return new Dimension(width, height)
		}
	}

	@Override
	Point getWindowPosition(int numOfWindows, int index) {
		validateIndex(numOfWindows, index)
		int x = basePoint.x + (index % 2) * this.getWindowDimension(numOfWindows, index).width
		int y = basePoint.y + Math.floor(index / 2) * this.getWindowDimension(numOfWindows, index).height
		return new Point(x, y)
	}


	/**
	 * Builder by Effective Java
	 */
	public static class Builder {
		// Required parameters

		// Optional parameters - initialized to default values
		private Dimension physicalScreenSize = Toolkit.getDefaultToolkit().getScreenSize()
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
				(int)(builder.physicalScreenSize.width - builder.basePoint.x * 2),
				(int)(builder.physicalScreenSize.height - builder.basePoint.y * 2)
				)
		basePoint = builder.basePoint
	}
}