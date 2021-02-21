package com.kazurayam.ks

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import java.awt.Dimension
import java.awt.Toolkit
import java.awt.Point

public class TileManager {

	private int numberOfTiles
	private Dimension physicalScreenSize

	TileManager(int numberOfTiles) {
		this(numberOfTiles, Toolkit.getDefaultToolkit().getScreenSize())	
	}
	
	TileManager(int numberOfTiles, Dimension physicalScreenSize) {
		if (numberOfTiles < 1) {
			throw new IllegalArgumentException("numberOfTiles(${numberOfTiles}) must be > 0")
		}
		this.numberOfTiles = numberOfTiles
		this.physicalScreenSize = physicalScreenSize
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
