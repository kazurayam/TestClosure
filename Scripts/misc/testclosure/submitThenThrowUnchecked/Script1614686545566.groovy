import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

import com.kms.katalon.core.exception.StepFailedException


/**
 * http://www.javabyexamples.com/handling-exceptions-from-executorservice-tasks
 * 2.1 Default Behavior with Callable
 * 
 */

ExecutorService executorService = Executors.newFixedThreadPool(1)

Closure cls = {
	println "I will throw StepFailedException now."
	throw new StepFailedException("Planned exception after execute()")
}
Future<Object> futureHandle = executorService.submit(cls)

executorService.shutdown()
