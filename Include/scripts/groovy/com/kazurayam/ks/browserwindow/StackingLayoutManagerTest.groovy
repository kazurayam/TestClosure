package com.kazurayam.ks.browserwindow

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

import static org.junit.Assert.*

import java.awt.Dimension
import java.awt.Point

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.browserwindow.StackingLayoutManager

@RunWith(JUnit4.class)
public class StackingLayoutManagerTest {
	
	StackingLayoutManager lm
	
	@Before
	void setup() {
		lm = new StackingLayoutManager(4, new Dimension(1280, 1024))
	}
	
	@Test
	void test_getNumberOfWindows() {
		assertTrue("expected 4 windows", lm.getNumberOfWindows() == 4)
	}
	
	@Test
	void test_getWindowDimension() {
		assertTrue("width=${lm.getWindowDimension().width}, expected 1280",
			lm.getWindowDimension().width == 1280)
		assertTrue("height=${lm.getWindowDimension().height}, epected 1024", 
			lm.getWindowDimension().height == 1024)
	}
	
	@Test
	void testWindow0() {
		Point pos = lm.getPosition(0)
		assertTrue("pos.x=${pos.x}, expected to be equal to ${lm.getDisposition().width * 0}",
			pos.x == lm.getDisposition().width * 0)
		assertTrue("pos.y=${pos.y}, expected to be equal to ${lm.getDisposition().height * 0}",
			pos.y == lm.getDisposition().height * 0)
	}
	
	@Test
	void testWindow1() {
		Point pos = lm.getPosition(1)
		assertTrue("pos.x=${pos.x}, expected to be equal to ${lm.getDisposition().width * 1}",
			pos.x == lm.getDisposition().width * 1)
		assertTrue("pos.y=${pos.y}, expected to be equal to ${lm.getDisposition().height * 1}",
			pos.y== lm.getDisposition().height * 1)
	}
	
	@Test
	void testWindow2() {
		Point pos = lm.getPosition(2)
		assertTrue("pos.x=${pos.x}, expected to be equal to ${lm.getDisposition().width * 2}",
			pos.x == lm.getDisposition().width * 2)
		assertTrue("pos.y=${pos.y}, expected to be equal to ${lm.getDisposition().height * 2}",
			pos.y == lm.getDisposition().height * 2)
	}
}
