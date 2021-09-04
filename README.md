# TestClosure

This is a [Katalon Studio](https://www.katalon.com/katalon-studio/) project for demonstration purpose. You can download the zip from the [Releases](https://github.com/kazurayam/TestClosure/releases) page, unzip it, open it with your local Katalon Studio.

This project was developed using Katalon Studiio v7.9.1. But it should work on all KS 7.0+.

This project proposes a new concept **TestClosure** for Katalon Studio. This project provides a preliminary implementation and demonbstrations as a proof of concept.



## Problem to solve

Many of Katalon Studio users want to execute multiple test processings *parallelly*. Me too. They have their own requirements. In my case, *I want to take a lot of web page screenshots as quickly as possible.*

As I wrote in the README of [ExecutionProfilesLoader project](https://github.com/kazurayam/ExecutionProfilesLoader), last year I wanted to take screenshots of 6000 URLs. The job took me 6000 * 10 seconds = over 17 hours. Obviously it's too long. I wanted to make the job run faster. 

It is a simple job to take a screenshot of a web page. 

>*Open browser, navigate to URL, take screenshot, save image to file, close browser*. 

Every time we navigate to a new URL, we are forced to wait for a few seconds until the page is fully loaded. This wait makes our sequential processing very slow.

If I have a machine with 8 Core-CPU, I can process these 6000 pages with 8 threads, then the job will be done in 17 hours / 8 threads = 2.2 hours.

But how can I do multi-threading in a Test Case of Katalon Studio?

I know that Katalon Studio offers [a feature of executing Test Suites in parallel mode](https://docs.katalon.com/katalon-studio/docs/test-suite-collection.html#manage-execution-information). It isn't satisfactory for me. It is too coarse-grained. I want a way to execute a piece of Groovy scripts in fine-grained / light-weighted Java Threads.

## Solution

### 1. You can call Groovy Closure in Katalon Test Case
d
It is quite easy to create a Groovy [Closure](https://www.baeldung.com/groovy-closures) that contains `WebUI.*` statements in a Test Case. Executing it is a breeze. See the following example.

- code [Test Cases/solution/01_callClosure](Scripts/solution/01_callClosure/Script1614148921401.groovy)
- video [01_callClosure](https://drive.google.com/file/d/1TfVjI36NQ-QQt52fQTX_94QGdMo3B5Bv/view?usp=sharing)

When you execute this, you will see a browser window opens, shows a web page, and closes.

### 2. You can excute Groovy Closures using ExecutorService utility

[`java.util.concurrent.ExecutorService`](https://www.baeldung.com/java-executor-service-tutorial) makes conncurrent programming in Java/Groovy much easier. A Groovy Closure implemwents the [`java.util.concurrent.Callable`](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Callable.html) interface. You can execute Closures of `WebUI.*` statements using `ExecutorSerivce` utility in a Katalon Test Case.

The following sample code shows 2 Closures are in 2 threads sequantially in Katalon Test Case.

- code [Test Cases/solution/02_executeClosuresByExecutorService](Scripts/solution/02_executeClosuresByExecutorService/Script1614264505453.groovy)
- video [02_executeClosuresByExecutorService](https://drive.google.com/file/d/1TCy1_gYpE_jFJwNydtRcX114wPUO5TGH/view?usp=sharing)

When you execute this code, you will see 2 browser windows opened/closed sequentially.

### 3. You can parametrize Closures

I want to parametrize a Closure. I want to execute 2 instances of a Groovy Closure with different values of parameters. The following code shows how to do it. I made a Groovy Class `URLVisitor` which implements the `java.util.concurrent.Callable` interface. Its constructor accepts parameters.

- code [Test Cases/solution/03_executeClosuresByExecutorService](Scripts/solution/03_executeCallablesSingleThread/Script1614249445525.groovy)
- video [03_executeCallablesSingleThread](https://drive.google.com/file/d/1qBZS_sUnziRsb5zouB_D9jQzHPQ4WXHk/view?usp=sharing)

When you execute this code, you will see 2 browser windows opened/closed sequentially.

### 4. You can only see 1 browser window

Now I want to execute 2 Closures simultaneously, in parallel. The following code does that:

The code difference between this and the previous one is only 1 character. The size of Thread Pool is changed: 1 -> 4.
```
ExecutorService exService = Executors.newFixedThreadPool(4)
```

Of course I would expect to see 2 Browser windows to open. 

- code [Test Cases/solutions/04_executeCallablesMultiThreadsLayoutUnmanaged](Scripts/solution/04_executeCallablesMultiThreadsLayoutUnmanaged/Script1614249737974.groovy)
- video [04_executeCallablesMultiThreadsLayoutUnmanaged](https://drive.google.com/file/d/1AJ7R1Gu2R4eagsUskTDzAeyr10FUmNcb/view?usp=sharing)


When I executed this example, I got puzzled. *I executed 2 threads in parallel, and I saw only 1 window opened. why?*

What you see is not what you get, sometimes. The fact is, acutally 2 windows were opened by the script, but the windows were displayed overlayed at the same position (x, y coordinate) with just the same dimension (width, height). So I could see only one. This behavior is very confusing.

### 5. Introduce TestClosure to manage layout of browser windows

I want to manage the layout of browser windows. I want them displayed at different positions (x, y) so that I can see them identifiable. But how can I do it?

WebDriver API provides tools for us to solve this problem. You can explicitly set Window Position and Size. For example,

```
driver.manage().window().setPosition(new Point(50,200));
```

and

```
driver.manage().window().setSize(new Dimension(300,500));
```

I want to call WebDriver API to set position and size to the windows opened by `WebUI.openBrowser('')` call inside the Closures in Test Cases. The following code shows how I managed it.

- code [Test Cases/solutions/05_executeCallablesMultiThreadsLayoutManaged](Scripts/solution/05_executeTestClosuresMultiThreadsLayoutManaged/Script1614249845231.groovy)
- video [05_executeTestClosuresMultiThreadsLayoutManaged](https://drive.google.com/file/d/112Yq5kQGnpycEOPn5sF-PD-qBs9I8OIB/view?usp=sharing)

The framework [`com.kazurayam.ks.testclosure.TestClosureCollectionExecutor`](Keywords/Keywords/com/kazurayam/ks/testclosure/TestClosureCollectionExecutor.groovy) manages opening/closing browser,
as well as positioning the browser windows. A TestClosure will be pass an instance of `WebDriver` as argument. The TestClosure just use the driver.
The TestClosure is not responsible for managing the browsers at all. 

Problems are resolved.

## Miscellaneous demo videos

You can view the videos by clicking the following links: 

| demo video | Test Suite elasped (seconds) | what it does |
|------------|---:|---|
| [demo1A](https://drive.google.com/file/d/1_IPbM2UniK6N1eudSlNKsVoolJCE-HLy/view?usp=sharing) | 27 | visits 3 URLs sequentially, windows are not managed|
| [demo1B](https://drive.google.com/file/d/1ZxymFzjS1NFCaAm-CQaFbrG0r-QsXLqp/view?usp=sharing) | 30 | visits 3 URLs sequetioally, windows are managed in Tile layout|
| [demo1C](https://drive.google.com/file/d/1SklnOF8-Kov4hm0mHDddHxaDaafs6LDK/view?usp=sharing) | 55 | visits 5 URLs sequentially, windows are managed in Stack layout, takes screenshots |
| [demo2A](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing) | 20 | visits 3 URLs simultaneously, windows are overlayed at the same (x,y) position with the same width/height|
| [demo2B](https://drive.google.com/file/d/1-MKKXkGclWOOsdvKgrL5Z7DyWRt_TemH/view?usp=sharing) | 21 | visits 3 URLs simultaneously, windows are managed in Tile layout |
| [demo2C](https://drive.google.com/file/d/1xkq50B8gOLIskDTrjad_fJ-ZC_5M9mZK/view?usp=sharing) | 48 | visits 5 URLs simultaneously, windows are managed in Stacklayout, takes screenshots |
| [demo3D](Scripts/demo/demo3D_ScreenshootingReusingWindowForMultipleURLs/Script1614228411232.groovy) | 156 | takes Full Page screenshots of 8 URLs using 2 browsers simultaneously. A browser is reused to process 4 URLs each. Unfortunately `WebUI.takeFullPageScreenshot()` takes long time (approx. 30 seconds each). Unable to post video for demo. |


Using the following environment:
- Mac Book Air, Processor: 1.6GHz Dual Core Intel Core i5, Memory 16GB
- macOS Big Sur
- Katalon Studio v7.9.1

**Caution: videos are compressed**

When I executed a Test Suite in Katalon Studio to take the [demo2A](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing), the Test Suite acutally took 20 seconds to finish processing. But you will find the ["movie"](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing) plays in 7 seconds. The movie plays far quicker than the acutal scene. I suppose that, while I uploaded the files to Google Drive, the movies were compressed to reduce the size by chomping the still frames off.


## How to install the plug-in

A plug-in jar named `kazurayam-ks-testobject-x.x.x.jar` is available at the [Releases](https://github.com/kazurayam/TestClosure/releases) page.
This jar contains `com.kazurayam.ks.testclosure.TestClosure` and related classes. Once you install this jar into your project's `Plugins` directory, 
you can quickly start multi-threading in Katalon Studio.

## Design Description 

This project includes a set of working codes. Please read the source to find the detail. The code will tell you everything I could.

Here I write quick pointers:

### Entry point

Have a look at a Test Case that opens 3 browser windows simultaneously:

- [Test Cases/demo/demo2B_simultaneous_tiling](Scripts/demo/demo2B_simultaneous_tiling/Script1614138730541.groovy)

The code is very short. Is it simple? --- not really. This short code triggers a lot.

### What is TestClosure? How to write it?

Essentially a `TestClosure` object is a pair of a Groovy Closure and a list of parameters for the closure. Plus a `TestClosure` carries information where the browser window should be located and how it should be sized. The source of `TestClosure` is this:

- [`com.kazurayam.ks.testclosure.TestClosure`](Keywords/com/kazurayam/ks/testclosure/TestClosure.groovy)

The following Test Case shows an example of creating a list of `TestClosure` objects.

- [Test Cases/demo/createTestClosures4Demo](Scripts/demo/createTestClosures4Demo/Script1614138730543.groovy)


### How to execute TestClosures in parallel?

The Test Case [`Test Cases/demo/demo2B_simultaneous_tiling`](Scripts/demo/demo2B_simultaneous_tiling/Script1614138730541.groovy) calles `TestClosureCollectionExcecutor`. This object executes the collection of `TestClosures` simultaneoutly.

The source code is here:

- [`com.kazurayam.ks.testclosure.TestClosuresCollectionExecutor`](Keywords/com/kazurayam/ks/testclosure/TestClosureCollectionExecutor.groovy)

`TestClosuresCollectionExecutor` creates a thread pool of 4 as default. You can change the maxThread by parameter to the Buiilder. The value 1 - 16 is accepted.

### 2 strategies of window layout 

This project provides 2 strategies of browser window layout:

- Tiling : see [demo2B](https://drive.google.com/file/d/1-MKKXkGclWOOsdvKgrL5Z7DyWRt_TemH/view?usp=sharing) for example
- Stacking : see [demo2C](https://drive.google.com/file/d/1xkq50B8gOLIskDTrjad_fJ-ZC_5M9mZK/view?usp=sharing) for example

`TestClosuresCollectionExecutor`  uses the Tiling strategy as default. You can explicitly specify the strategy as a parameter to the Builder.

### Multiple threads --- does it run faster?

Yes, it does on a machine with 2 or more core-CPU.  If my test case executes mutilple TestClosures with multiple threads, it rus faster than with a single thread.

Of course, the faster processing demands more powerful machine resources. If I can afford Mac Book Air M1 with 8-core CPU, my test case with Thread Pool of 8 will run far faster. I want to see it!

It is pointless to give the maximum number of threads for `TestClosureCollectionExceutor` with a value larger (8, 16, 32) than the number of cores you have.

## Conclusion

I could implement a Test Case that executes multiple Groovy Closures with multiple Threads. The code ran faster on my dual-core machine than with single Thread.

The codes of this project is yet preliminary. It has a lot more to develop. Especially, it does not consier failure handling seriously.
