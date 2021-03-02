import java.lang.Thread.UncaughtExceptionHandler
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

import com.kms.katalon.core.exception.StepFailedException

/**
 * http://www.javabyexamples.com/handling-exceptions-from-executorservice-tasks
 * 4. Handle with Wrapper Task
 * 
 * We'll now investigate how we can handle an uncaught exception
 * wrapping the original task. The previous
 * UncaughtExceptionHandler approach applies to all threads
 * and tasks in a thread pool.
 * However, if we're running different tasks in the same thread pool
 * and they require different exception handling logic, this may not
 * be optimal. Or we aren't even allowed to set a handler because
 * the task submission code is using a preconfigured pool.
 * In these cases, we can wrap our original task in another 
 * Runnable or Callable. The wrapper class catches the exception
 * and takes the appropriate action.
 */

class CatchingRunnable implements Runnable {
	private final Runnable delegate
	CatchingRunnable(Runnable delegate) {
		this.delegate = delegate
	}
	@Override
	void run() {
		try {
			delegate.run()
		} catch (RuntimeException e) {
			println e.getMessage()  // log, notify etc...
			throw e
		}
	}
}

ExecutorService executorService = Executors.newFixedThreadPool(1)

Closure cls = {
	println "I will throw RuntimeException now."
	throw new RuntimeException("Planned exception after execute()")
}

CatchingRunnable catchingRunnable = new CatchingRunnable(cls)

executorService.execute(catchingRunnable)

executorService.shutdown()
