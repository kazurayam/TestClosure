package com.kazurayam.ks.windowlayout

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class BrowserWindowLayoutKeywordTest {

	TilingLayoutMetrics tilingLayout
	StackingLayoutMetrics stackingLayout
	
	@Before
	void setup() {
		tilingLayout = new TilingLayoutMetrics.Builder().build()
		stackingLayout = new StackingLayoutMetrics.Builder().build()	
	}

	@Test
	void test_tiling() {
		String url = "http://demoaut.katalon.com/"
		WebUI.openBrowser('')
		WebUI.navigateToUrl(url)
		BrowserWindowLayoutKeyword.layout(tilingLayout, new WindowLocation(2, 0))
		WebUI.delay(1)
		BrowserWindowLayoutKeyword.layout(tilingLayout, new WindowLocation(2, 1))
		WebUI.delay(1)
		BrowserWindowLayoutKeyword.layout(tilingLayout, new WindowLocation(4, 0))
		WebUI.delay(1)
		BrowserWindowLayoutKeyword.layout(tilingLayout, new WindowLocation(4, 1))
		WebUI.delay(1)
		BrowserWindowLayoutKeyword.layout(tilingLayout, new WindowLocation(4, 2))
		WebUI.delay(1)
		BrowserWindowLayoutKeyword.layout(tilingLayout, new WindowLocation(4, 3))
		WebUI.delay(1)
		WebUI.closeBrowser()
	}
	
	@Test
	void test_stacking() {
		String url = "http://demoaut-mimic.kazurayam.com/"
		WebUI.openBrowser('')
		WebUI.navigateToUrl(url)
		BrowserWindowLayoutKeyword.layout(stackingLayout, new WindowLocation(4, 0))
		WebUI.delay(1)
		BrowserWindowLayoutKeyword.layout(stackingLayout, new WindowLocation(4, 1))
		WebUI.delay(1)
		BrowserWindowLayoutKeyword.layout(stackingLayout, new WindowLocation(4, 2))
		WebUI.delay(1)
		BrowserWindowLayoutKeyword.layout(stackingLayout, new WindowLocation(4, 3))
		WebUI.delay(1)
		WebUI.closeBrowser()
	}
}
