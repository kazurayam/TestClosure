# TestClosure

This is a [Katalon Studio](https://www.katalon.com/katalon-studio/) project for demonstration purpose. You can download the zip from the [Releases](https://github.com/kazurayam/TestClosure/releases) page, unzip it, open it with your local Katalon Studio.

This project was developed using Katalon Studiio v7.9.1. But it should work on all KS 7.0+.

This project proposes a new concept **TestClosure** for Katalon Studio. This project provides a preliminary implementation and demonbstrations as a proof of concept.



## Problem to solve

Many of Katalon Studio users want to execute multiple test processings *parallelly*. Me too. They have their own requirements. In my case, *I want to take a lot of web page screenshots as quick as possible*.

As I wrote in the README of [ExecutionProfilesLoader project](https://github.com/kazurayam/ExecutionProfilesLoader), last year I wanted to take screenshots of 6000 URLs. The job took me 6000 * 10 seconds = over 17 hours. Obviously it's too long. I wanted to make the job run faster. It is a simple job to take a screenshot of a web page. 

>*Open browser, navigate to URL, take screenshot, save image to file, close browser*. 

If I can process these 6000 pages with 8 threads, then the job will be done in 17 hours / 8 threads = 2.2 hours.

But how can I do multi-threading in a Test Case of Katalon Studio?

I know that Katalon Studio offers [a feature of executing Test Suites in parallel mode](https://docs.katalon.com/katalon-studio/docs/test-suite-collection.html#manage-execution-information). It does't satisfactory for me. It is too coarse-grained. I want a way to execute a piece of Groovy scripts in fine-grained / light-weighted Java Threads.

## Solution

### execute Multipe Groovy Closures in Test Case

It is quite easy to create a Groovy [Closure](https://www.baeldung.com/groovy-closures) that contains `WebUI.*` statements in a Test Case. Executing it is a breeze. See the following example.

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
When you execute this, you will see a browser window opens, shows a web page, and closes.

A Groovy Closure implemwents the [`java.util.concurrent.Callable`](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Callable.html) interface. This means, you can execute Closure objects using [`java.util.concurrent.ExecutorService`](https://www.baeldung.com/java-executor-service-tutorial). Therefore you can easily execute Closures of `WebUI.*` statements using the `ExecutorSerivce` utility in a Katalon Test Case.

The following sample code shows how to execute 2 Closures sequantially in Katalon Test Case. This code shows how to parameterise Closures as well.

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

When you execute this code, you will see 2 browser windows opened/closed sequentially.


### 2. Should Manage Layout of Browser windows

Now I want to try openig 2 browser windows simultaneously using multiple threads. The following code does that:

[Test Cases/solution/03_executeClosuresByMultipleThreads](Scripts/solution/03_exeuteClosuresByMultipleThreads/Script1614155016466.groovy)

The difference of `03_executeClosuresByMultipleThreads` and `02_executeClosuresBySigngleThreads` is only 1 character: the size of Thread Pool: 1 -> 4.

```
ExecutorService exService = Executors.newFixedThreadPool(4)
```

When you execute this example, you will get puzzled. *I only see 1 window opened for 2 threads, why?* See the following video:

- [Why 1 window?](https://drive.google.com/file/d/1xHTVUhEGo041zHxvFyyUPAnGS1gWIIwc/view?usp=sharing)

*What you saw is not what you got*. The fact is that acutally 2 windows were opened by [Test Cases/solution/03_executeClosuresByMultipleThreads](Scripts/solution/03_exeuteClosuresByMultipleThreads/Script1614155016466.groovy), but they were displayed overlayed at the same position (x, y coordinate) with just the same dimension (width, height). 

This is very confusing. I want to manage the layout of browser windows; I want them displayed at different positions (x, y) so that I can see them identifiable.

WebDriver API provides solutions for us. You can explicitly set Window Position and Size by WebDriver API. For example,

```
driver.manage().window().setPosition(new Point(50,200));
```

and

```
driver.manage().window().setSize(new Dimension(300,500));
```

See, for example, http://www.software-testing-tutorials-automation.com/2015/02/how-to-setget-window-position-and-size.html

I want to apply this WebDriver API call to the windows opened by `WebUI.openBrowser('')` call inside the Closures in Test Cases.



## Demo videos summary

You can view the videos by clicking the following links: 

| demo video | Test Suite elasped (seconds) | what it does |
|------------|---:|---|
| [demo1A](https://drive.google.com/file/d/1_IPbM2UniK6N1eudSlNKsVoolJCE-HLy/view?usp=sharing) | 27 | visits 3 URLs sequentially, windows are not managed|
| [demo1B](https://drive.google.com/file/d/1ZxymFzjS1NFCaAm-CQaFbrG0r-QsXLqp/view?usp=sharing) | 30 | visits 3 URLs sequetioally, windows are managed in Tile layout|
| [demo1C](https://drive.google.com/file/d/1SklnOF8-Kov4hm0mHDddHxaDaafs6LDK/view?usp=sharing) | 55 | visits 5 URLs sequentially, windows are managed in Stack layout, takes screenshots |
| [demo2A](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing) | 20 | visits 3 URLs simultaneously, windows are overlayed at the same (x,y) position with the same width/height|
| [demo2B](https://drive.google.com/file/d/1-MKKXkGclWOOsdvKgrL5Z7DyWRt_TemH/view?usp=sharing) | 21 | visits 3 URLs simultaneously, windows are managed in Tile layout |
| [demo2C](https://drive.google.com/file/d/1xkq50B8gOLIskDTrjad_fJ-ZC_5M9mZK/view?usp=sharing) | 48 | visits 5 URLs simultaneously, windows are managed in Stacklayout, takes screenshots |

Using the following environment:
- Mac Book Air, Processor: 1.6GHz Dual Core Intel Core i5, Memory 16GB
- macOS Big Sur
- Katalon Studio v7.9.1

### Caution: videos are compressed

When I executed a Test Suite in Katalon Studio to take the [demo2A](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing), the Test Suite acutally took 20 seconds to finish processing. But you will find the ["movie"](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing) plays in 7 seconds. The movie plays far quicker than the acutal scene. I suppose that, while I uploaded the files, the movies are compressed to reduce the size by chomping the still frames off.

## Description 

This project includes a set of working codes. I will add some comments and quick pointers to the codes to read:

### Entry point

Have a look at a Test Case that opens 3 browser windows simultaneously:

- [Test Cases/demo/demo2B_simultaneous_tiling](Scripts/demo/demo2B_simultaneous_tiling/Script1614138730541.groovy)

The code is short. Is it simple? --- not really. This short code triggers a lot.

### What is TestClosure? How to write it?

Essentially a `TestClosure` object is a pair of a Groovy Closure and a list of parameters for the closure. Plus a `TestClosure` carries information where the browser window should be located and how it should be sized. The source of `TestClosure` is this:

- [`com.kazurayam.ks.testclosure.TestClosure`](Keywords/com/kazurayam/ks/testclosure/TestClosure.groovy)

The following Test Case shows an example of creating a list of `TestClosure` objects.

- [Test Cases/demo/createTestClosures4Demo](Scripts/demo/createTestClosures4Demo/Script1614138730543.groovy)


### How to execute TestClosures in parallel?

The Test Case [`Test Cases/demo/demo2B_simultaneous_tiling`](Scripts/demo/demo2B_simultaneous_tiling/Script1614138730541.groovy) calles `TestClosureCollectionExcecutor`. This object executes the collection of `TestClosures` simultaneoutly.

The source code is here:

- [`com.kazurayam.ks.testclosure.TestClosuresCollectionExecutor`](Keywords/com/kazurayam/ks/testclosure/TestClosureCollectionExecutor.groovy)

`TestClosuresCollectionExecutor` creates a thread pool of 4 as default. The Builder accepts the maxThread size. The value 1 - 32 is accepted.

### 2 strategies of window layout 

This project provides 2 strategies of browser window layout:

- Tiling : see [demo2B](https://drive.google.com/file/d/1-MKKXkGclWOOsdvKgrL5Z7DyWRt_TemH/view?usp=sharing) for example
- Stacking : see [demo2C](https://drive.google.com/file/d/1xkq50B8gOLIskDTrjad_fJ-ZC_5M9mZK/view?usp=sharing) for example

`TestClosuresCollectionExecutor`  uses the Tiling strategy as default, but you can specify the Stacking strategy.

### Multiple threads --- does it run faster?

Yes, it does, if my test case executes mutilple TestClosures with multiple threads than a single thread.

 I used a Mac with dual-core processor. So my test with 2 threads perfomed faster than with single thread. If I can afford Mac Book Air M1 with 8-core CPU, my test case will run much more faster.

It is pointless to set the maximum threads for `TestClosureCollectionExceutor` with value larger (8, 16, 32) than the number of cores you have.

## Conclusion

I could implement a Test Case that executes multiple Groovy Closures in multiple Threads 


## Other discussions







### 




