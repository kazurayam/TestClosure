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

// Multi-threaded
ExecutorService exService = Executors.newFixedThreadPool(4)
exService.invokeAll(callables)

// You will see 2 browser windows are displayed overlayed
// at the same position with same width/size.
// Effectively you will misunderstand that only 1 window was displayed.

exService.shutdown()
exService.awaitTermination(1, TimeUnit.SECONDS)

