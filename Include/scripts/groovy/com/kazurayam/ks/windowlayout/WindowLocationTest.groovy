package com.kazurayam.ks.windowlayout

import static org.junit.Assert.*

import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.Point as Point

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.windowlayout.WindowLocation
import com.kazurayam.ks.windowlayout.WindowLocationTest

@RunWith(JUnit4.class)
public class WindowLocationTest {

	@Before
	public void setup() {
	}

	@Test
	public void test_validate_pass() {
		try {
			WindowLocation.validate(1,0)
		} catch (IllegalArgumentException e) {
			fail("should not raise Exception")
		}
	}

	@Test
	public void test_validate_size_0() {
		try {
			WindowLocation.validate(0,0)
			fail("should raise Exception when size=0")
		} catch (IllegalArgumentException e) {
			;
		}
	}

	@Test
	public void test_validate_minus_index() {
		try {
			WindowLocation.validate(1,-1)
			fail("should raise Exception when index=-1")
		} catch (IllegalArgumentException e) {
			;
		}
	}

	@Test
	public void test_validate_index_equal_to_size() {
		try {
			WindowLocation.validate(1,1)
			fail("should raise Exception when index=1 when size=1")
		} catch (IllegalArgumentException e) {
			;
		}
	}

	@Test
	public void test_normal() {
		WindowLocation bwl = new WindowLocation(3, 0)
		assertEquals(3, bwl.size)
		assertEquals(0, bwl.index)
	}
}
