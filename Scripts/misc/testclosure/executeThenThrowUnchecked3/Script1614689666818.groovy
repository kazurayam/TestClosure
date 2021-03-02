import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 * http://www.javabyexamples.com/handling-exceptions-from-executorservice-tasks
 * 5. Handle with Overriding afterExecute
 */
class MonitoringThreadPoolExecutor extends ThreadPoolExecutor {
	MonitoringThreadPoolExecutor(int corePoolSize, int maximumPoolSize, 
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
	}
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t)
		if (t != null) {
			println "Exception message: " + t.getMessage()
		}
	}
}

ExecutorService executorService = 
	new MonitoringThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
		new LinkedBlockingQueue<>())

Closure cls = {
	println "I will throw RuntimeException now."
	throw new RuntimeException("Planned exception after execute()")
}
	
executorService.execute(cls)

executorService.shutdown()

