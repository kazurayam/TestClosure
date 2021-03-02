import java.util.concurrent.ExecutionException
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
Future<Object> future = executorService.submit(cls)

try {
	future.get()
} catch (InterruptedException e) {
	e.printStackTrace()
} catch (ExecutionException e) {
	e.printStackTrace()
	/*
	 * Here, when we invoke the get method, an ExecutionException 
	 * will be thrown wrapping the original RuntimeException.
	 * So we can conclude that even if a worker thread has an
	 * UncaughtExceptionHandler, the JDK won(t notify the handler 
	 * for an uncaught exception that occured in a Callable task.
	 */
}

executorService.shutdown()
