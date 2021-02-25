import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil

class URLVisitor implements Callable<String> {
	private final Closure closure
	private final String url
	URLVisitor(Closure closure, String url) {
		this.closure = closure
		this.url = url 
	}
	@Override
	String call() throws Exception {
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
List<Future<String>> futures = exService.invokeAll(callables, 30, TimeUnit.SECONDS)


// consume the returned values from the threads
for (ft in futures) {
	String result = null
	try {
		result = ft.get()
	} catch (InterruptedException | ExecutionException e) {
		e.printStackTrace()
	}
}

exService.shutdown()

try {
	if (!exService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
		println "after calling awaitTermination()"
		exService.shutdownNow()
		println "after calling shutdownNow()"
	}
} catch (InterruptedException e) {
	exService.shutdownNow()
	Thread.currentThread().interrupt();
}

KeywordUtil.logInfo("Heigh Ho")