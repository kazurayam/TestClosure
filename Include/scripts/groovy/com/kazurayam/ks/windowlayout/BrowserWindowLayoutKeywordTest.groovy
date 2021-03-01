package com.kazurayam.ks.windowlayout

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

@RunWith(JUnit4.class)
public class BrowserWindowLayoutKeywordTest {

	TilingWindowLayoutMetrics tilingLayout
	StackingWindowLayoutMetrics stackingLayout

	@Before
	void setup() {
		tilingLayout = new TilingWindowLayoutMetrics.Builder().build()
		stackingLayout = new StackingWindowLayoutMetrics.Builder().build()
	}

	@Test
	void test_tiling() {
		String url = "http://demoaut.katalon.com/"
		WebUI.openBrowser('')
		WebUI.navigateToUrl(url)
		List<WindowLocation> locations = [
			new WindowLocation(2, 0),
			new WindowLocation(2, 1),
			new WindowLocation(4, 0),
			new WindowLocation(4, 1),
			new WindowLocation(4, 2),
			new WindowLocation(4, 3),
		]
		locations.each { loc ->
			BrowserWindowLayoutKeyword.layout(
					tilingLayout.getWindowPosition(loc), tilingLayout.getWindowDimension(loc))
			WebUI.delay(1)
		}
		WebUI.closeBrowser()
	}

	@Test
	void test_stacking() {
		String url = "http://demoaut-mimic.kazurayam.com/"
		WebUI.openBrowser('')
		WebUI.navigateToUrl(url)
		List<WindowLocation> locations = [
			new WindowLocation(4, 0),
			new WindowLocation(4, 1),
			new WindowLocation(4, 2),
			new WindowLocation(4, 3),
		]
		locations.each { loc ->
			BrowserWindowLayoutKeyword.layout(
					stackingLayout.getWindowPosition(loc), tilingLayout.getWindowDimension(loc))
			WebUI.delay(1)
		}
		WebUI.closeBrowser()
	}
}
