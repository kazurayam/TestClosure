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
	
	/**
	 * 
	 */
	public static class Builder {
		// Required parameters
		private final int numberOfTiles
		
		// Optional parameters - initialized to default values
		private Dimension physicalScreenSize = Toolkit.getDefaultToolkit().getScreenSize()
		private Point basePoint = new Point(10, 10)
		
		public Builder(int numberOfTiles) {
			this.numberOfTiles = numberOfTiles
		}
		
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
		numberOfTiles = builder.numberOfTiles
		//
		Dimension virtualScreenSize = new Dimension(
			(int)(builder.physicalScreenSize.width - builder.basePoint.x * 2),
			(int)(builder.physicalScreenSize.height - builder.basePoint.y * 2)
			)
		if (builder.numberOfTiles < 1) {
			throw new IllegalArgumentException("numberOfTiles(${numberOfTiles}) must be > 0")
		} else if (builder.numberOfTiles == 1) {
			numberOfRows = 1
			numberOfColumns = 1
			tileDimension = virtualScreenSize
		} else {
			// Tiles in 2 columns
			numberOfRows = Math.ceil(builder.numberOfTiles / 2)
			numberOfColumns = 2
			int tileWidth = Math.floor(virtualScreenSize.width / 2)
			int rows = Math.ceil(builder.numberOfTiles / 2)
			int tileHight = Math.floor(virtualScreenSize.height / rows )
			tileDimension = new Dimension(tileWidth, tileHight)
		}
	}
}