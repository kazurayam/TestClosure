package com.kazurayam.ks.testclosure

import static org.junit.Assert.*

import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.Point

import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class TestClosureCollectionExecutorTest {

	TestClosureCollectionExecutor executor
	
	@Before
	void setup() {
		executor = new TestClosureCollectionExecutor()	
	}

	@Test
	void test_something() {
	}
}
