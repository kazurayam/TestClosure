package com.kazurayam.ks.windowlayout

import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.Point

import com.kazurayam.ks.windowlayout.WindowLocation

abstract class WindowLayoutMetrics {

	abstract Point getWindowPosition(WindowLocation windowLocation)

	abstract Dimension getWindowDimension(WindowLocation windowLocation)
}
