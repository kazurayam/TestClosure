package com.kazurayam.ks.browserwindow

import java.awt.Dimension
import java.awt.Point
import java.awt.Toolkit

public class TilingBrowserWindowManager implements BrowserWindowManager {

	private int numberOfTiles
	private Dimension tileDimension

	TilingBrowserWindowManager(int numberOfTiles) {
		this(numberOfTiles, Toolkit.getDefaultToolkit().getScreenSize())
	}

	TilingBrowserWindowManager(int numberOfTiles, Dimension physicalScreenSize) {
		this.numberOfTiles = numberOfTiles
		if (numberOfTiles < 1) {
			throw new IllegalArgumentException("numberOfTiles(${numberOfTiles}) must be > 0")
		} else if (numberOfTiles == 1) {
			this.tileDimension = physicalScreenSize   // full-screen
		} else {
			// Tiles in 2 columns
			int tileWidth = Math.floor(physicalScreenSize.width / 2)
			int rows = Math.ceil(numberOfTiles / 2)
			int tileHight = Math.floor(physicalScreenSize.height / rows )
			tileDimension = new Dimension(tileWidth, tileHight)
		}
	}

	int getNumberOfTiles() {
		return numberOfTiles
	}

	Dimension getTileDimension() {
		return tileDimension
	}

	Point getLocationOf(int tileIndex) {
		return new Point(10, 10)
	}

	Dimension getDimensionOf(int tileIndex) {
		int width = 800
		int height = 600
		return new Dimension(width, height)
	}
}
