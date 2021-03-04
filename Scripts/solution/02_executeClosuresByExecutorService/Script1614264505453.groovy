import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<Closure> closures = new ArrayList<Closure>()

closures.add({
	WebUI.openBrowser('')
	WebUI.navigateToUrl("http://demoaut.katalon.com/")
	WebUI.waitForPageLoad(10)
	WebUI.delay(1)
	WebUI.closeBrowser()
	return "OK"
})

closures.add({
	WebUI.openBrowser('')
	WebUI.navigateToUrl("https://duckduckgo.com/")
	WebUI.waitForPageLoad(10)
	WebUI.delay(1)
	WebUI.closeBrowser()
	return "OK"
})

ExecutorService exService = Executors.newFixedThreadPool(1)
exService.invokeAll(closures)

exService.shutdown()
try {
	if (!exService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
		exService.shutdownNow()
	}
} catch (InterruptedException e) {
	exService.shutdownNow()
}