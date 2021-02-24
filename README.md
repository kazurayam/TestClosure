# TestClosure

This is a [Katalon Studio](https://www.katalon.com/katalon-studio/) project for demonstration purpose. You can download the zip from [Releases](https://github.com/kazurayam/TestClosure/releases) page, unzip it, open it with your local Katalon Studio.

This project was developed using Katalon Studiio v7.9.1. But it should work on all KS 7.0+.

This project proposes a new concept **TestClosure** for Katalon Studio. This project provides a preliminary implementation and demonbstrations as a proof of concept.

## Demo videos summary

You can view the videos by clicking the following links: 

| demo video | Test Suite elasped (seconds) | |
|------------|--------------------|---|
| [demo1A](https://drive.google.com/file/d/1_IPbM2UniK6N1eudSlNKsVoolJCE-HLy/view?usp=sharing) | 27 | visits 3 URLs sequentially, windows are not managed|
| [demo1B](https://drive.google.com/file/d/1ZxymFzjS1NFCaAm-CQaFbrG0r-QsXLqp/view?usp=sharing) | 30 | visits 3 URLs sequetioally, windows are managed in Tile layout|
| [demo1C](https://drive.google.com/file/d/1SklnOF8-Kov4hm0mHDddHxaDaafs6LDK/view?usp=sharing) | 55 | visits 5 URLs sequentially, windows are managed in Stack layout, takes screenshots |
| [demo2A](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing) | 20 | visits 3 URLs simultaneously, windows are overlayed at the same (x,y) position with the same width/height|
| [demo2B](https://drive.google.com/file/d/1-MKKXkGclWOOsdvKgrL5Z7DyWRt_TemH/view?usp=sharing) | 21 | visits 3 URLs simultaneously, windows are managed in Tile layout |
| [demo2C](https://drive.google.com/file/d/1xkq50B8gOLIskDTrjad_fJ-ZC_5M9mZK/view?usp=sharing) | 48 | visits 5 URLs simultaneously, windows are managed in Stacklayout, takes screenshots |

- Mac Book Air, Processor: 1.6GHz Dual Core Intel Core i5, Memory 16GB
- macOS Big Sur
- Katalon Studio v7.9.1

### Caution: videos are compressed

When I executed a Test Suite in Katalon Studio to take the [demo2A](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing), it acutally took 20 seconds to finish processing. But you will find the ["movie"](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing) plays in 7 seconds. The "movie" plays far quicker than the acutal scene because the "movies" are compressed to reduce file size by chomping still frames off.



## Problem to solve

Many of Katalon Studio users want to execute multiple test processings *pararelly* vor various reasons. Me too.

As I wrote in the README of [ExecutionProfilesLoader project](https://github.com/kazurayam/ExecutionProfilesLoader), last year I wanted to take massive amount of web page screenshots, and that job took very long time: 6000 * 10 seconds = over 17 hours. I wanted to find out a way to do this job quicker.

If I can execute a job with multiple threads of processing --- open browser, navigate to URL, take screenshot, save image to file, close browser --- simultaniously (= parallely), the job will be done faster provided that I can afford sufficient hardware resources. I want to process 6000 web pages. I want to process these pages with, say 8 Java Thread Pools. If I can do it, I would be able to finish the job in 17 hours / 8 = 2.2 hours. Far quicker! I want to achieve it. 

But how can I execute multiple threads in Katalon Studio? 

I know that Katalon Studio offers [a feature of executing Test Suites in parallel mode](https://docs.katalon.com/katalon-studio/docs/test-suite-collection.html#manage-execution-information). It does not satisfies me. It is too heavy-waited. I want a way to execute a piece of Groovy scripts in fly-weighted Java Threads.

## Solution

### 1. Can execute multipe Groovy Closures in Katalon Test Case

It is quite easy to construct a Groovy [Closure](https://www.baeldung.com/groovy-closures) that contains `WebUI.*` statements in a Test Case. Executing it is a breeze. See the following example.

[Test Cases/solution/01_callClosure](Scripts/solution/01_callClosure/Script1614148921401.groovy)
```
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Closure closure = {
	WebUI.openBrowser('')
	WebUI.navigateToUrl("http://demoaut.katalon.com/")
	WebUI.waitForPageLoad(10)
	WebUI.delay(3)
	WebUI.closeBrowser()
}

closure.call()
```
When you execute this test case, you will see a browser window opens, shows the web page, and closes.

A Groovy Closure implemwents [`java.util.concurrent.Callable`](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Callable.html) interface. This means, you can execute multiple Closures using [`java.util.concurrent.ExecutorService`](https://www.baeldung.com/java-executor-service-tutorial). Therefore is easy to execute Closures of `WebUI.*` using ExecutorSerivce in a Katalon Test Case. 

The following sample code shows how to execute 2 Closures sequantially in Katalon Test Case. This code shows how to parameterise Closures.

[Test Cases/solution/02_executeClosuresBySingleThread](Scripts/solution/02_exeuteClosuresBySingleThread/Script1614154169248.groovy)
```
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class URLVisitor implements Callable<String> {
	private final Closure closure
	private final String url
	URLVisitor(Closure closure, String url) {
		this.closure = closure
		this.url = url 
	}
	String call() {
		closure.call(url)
		return "OK"
	}
}

Closure closure = { url ->
	WebUI.openBrowser('')
	WebUI.navigateToUrl(url)
	WebUI.waitForPageLoad(10)
	WebUI.delay(3)
	WebUI.closeBrowser()
}

// create Callable closures with param values
List<Callable> callables = new ArrayList<Callable>()
callables.add(new URLVisitor(closure, "http://demoaut.katalon.com/"))
callables.add(new URLVisitor(closure, "https://duckduckgo.com/"))

// Single threaded
ExecutorService exService = Executors.newFixedThreadPool(1)
exService.invokeAll(callables)

exService.shutdown()
exService.awaitTermination(1, TimeUnit.SECONDS)
```

When you execute this code, you will see 2 browser windows opened/closed sequentially by Single Thread.




### 2. Should Manage Layout of Browser windows

Now I want to try openig 2 browser windows simultaneously using the pool of threads. The following code does that:


[Test Cases/solution/03_executeClosuresByMultipleThreads](Scripts/solution/03_exeuteClosuresByMultipleThreads/Script1614155016466.groovy)

The only difference of `03_executeClosuresByMultipleThreads` from `02_executeClosuresBySigngleThreads` is a line where specifies the sides of Thread pool:
```
ExecutorService exService = Executors.newFixedThreadPool(4)
```

When you execute `03_executeClosuresByMultipleThreads`, you will get puzzled. *Why do you see only 1 window opened for 2 threads?* 

See the following video:

- [Why 1 window?](https://drive.google.com/file/d/1xHTVUhEGo041zHxvFyyUPAnGS1gWIIwc/view?usp=sharing)

The fact is that acutally 2 windows are opened by [Test Cases/solution/03_executeClosuresByMultipleThreads](Scripts/solution/03_exeuteClosuresByMultipleThreads/Script1614155016466.groovy), but they are displayed overlayed at the same position (x, y) with just the same dimension (with, height). It is very confusing if multiple windows driven by multiple threads are displayed in this way. I should develop some solution to this. Somehow I want to manage the layout of browser windows opened by multiple threads of `WebUI.openBrowser()` at different positions (x, y) so that I can see them identifiable.

You can set Window Position and Size by WebDriver API. 
```
driver.manage().window().setPosition(new Point(50,200));
```
and
```
driver.manage().window().setSize(new Dimension(300,500));
```

See, for example, http://www.software-testing-tutorials-automation.com/2015/02/how-to-setget-window-position-and-size.html



## Preliminary implementation

This project includes working implementation. You can read the Groovy source code that I wrote. Please find detail info by reading them.

I will list some quick pointers to codes:

### What is TestClosure? How to write it?

Essentially a `TestClosure` object is a pair of a Groovy Closure and a list of parameters.

Plus a `TestClosure` carries information where the browser window should be located and how it should be sized.

The following Test Case shows an example of creating a list of `TestClosure` objects.

- [Test Cases/demo/createTestClosures4Demo](Scripts/demo/createTestClosures4Demo/Script1614138730543.groovy)

The source of `TestClosure` is this:

- [`com.kazurayam.ks.testclosure.TestClosure`](Keywords/com/kazurayam/ks/testclosure/TestClosure.groovy)

### How to execute TestClosures in parallel?


### 2 strategies of window layout 

Tiling

Stacking

## Conclusion


## Other discussions







### 




