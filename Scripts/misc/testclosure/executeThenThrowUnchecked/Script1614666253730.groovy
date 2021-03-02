import java.lang.Thread.UncaughtExceptionHandler
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

import com.kms.katalon.core.exception.StepFailedException

/**
 * http://www.javabyexamples.com/handling-exceptions-from-executorservice-tasks
 */

class AppExceptionHandler implements UncaughtExceptionHandler {
	@Override
	void uncaughtException(Thread t, Throwable e) {
		println "Uncaught Exception occured on thread: " + t.getName()
		println "Exception message: " + e.getMessage()
	}
}

class AppThreadFactory implements ThreadFactory {
	@Override
	public Thread newThread(Runnable r) {
		final Thread thread = new Thread(r)
		thread.setUncaughtExceptionHandler(new AppExceptionHandler())
		return thread
	}
}

ExecutorService executorService = 
	Executors.newFixedThreadPool(1, new AppThreadFactory())

Closure cls = {
	println "I will throw StepFailedException now."
	throw new RuntimeException("Planned exception after execute()")
}
executorService.execute(cls)

executorService.shutdown()
