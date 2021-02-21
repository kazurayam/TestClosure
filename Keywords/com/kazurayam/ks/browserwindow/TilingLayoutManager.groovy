package com.kazurayam.ks.browserwindow

import java.awt.Dimension
import java.awt.Point
import java.awt.Toolkit

public class TilingLayoutManager implements BrowserWindowsLayoutManager {

	private final int numberOfTiles
	private final int numberOfRows
	private final int numberOfColumns
	private final Dimension tileDimension

	private final Point basePoint = new Point(10, 10)

	TilingLayoutManager(int numberOfTiles) {
		this(numberOfTiles, Toolkit.getDefaultToolkit().getScreenSize())
	}

	TilingLayoutManager(int numberOfTiles, Dimension physicalScreenSize) {
		this.numberOfTiles = numberOfTiles
		Dimension virtualScreenSize = new Dimension(
				(int)(physicalScreenSize.width - basePoint.x * 2),
				(int)(physicalScreenSize.height - basePoint.y * 2)
				)
		if (numberOfTiles < 1) {
			throw new IllegalArgumentException("numberOfTiles(${numberOfTiles}) must be > 0")
		} else if (numberOfTiles == 1) {
			this.numberOfRows = 1
			this.numberOfColumns = 1
			this.tileDimension = virtualScreenSize
		} else {
			// Tiles in 2 columns
			this.numberOfRows = Math.ceil(numberOfTiles / 2)
			this.numberOfColumns = 2
			int tileWidth = Math.floor(virtualScreenSize.width / 2)
			int rows = Math.ceil(numberOfTiles / 2)
			int tileHight = Math.floor(virtualScreenSize.height / rows )
			this.tileDimension = new Dimension(tileWidth, tileHight)
		}
	}

	int getNumberOfTiles() {
		return numberOfTiles
	}

	int getNumberOfRows() {
		return numberOfRows
	}

	int getNumberOfColumns() {
		return numberOfColumns
	}

	Point getBasePoint() {
		return basePoint
	}

	Dimension getTileDimension() {
		return tileDimension
	}

	@Override
	Point getPosition(int tileIndex) {
		validateTileIndex(tileIndex)
		int x = basePoint.x + (tileIndex % 2) * tileDimension.width
		int y = basePoint.y + Math.floor(tileIndex / 2) * tileDimension.height
		return new Point(x, y)
	}

	@Override
	Dimension getDimension(int tileIndex) {
		validateTileIndex(tileIndex)
		return tileDimension
	}

	private validateTileIndex(int tileIndex) {
		if (tileIndex < 0) {
			throw new IllegalArgumentException("tileIndex must be >= 0")
		}
		if (tileIndex >= this.numberOfTiles) {
			throw new IllegalArgumentException("tileIndex must be less than numberOfTiles(${numberOfTiles})")
		}
	}
}
