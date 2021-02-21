package com.kazurayam.ks

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * 
 * @author kazurayam
 */
public class TestClosuresExecutor {

	List<Callable<String>> callableTasks

	public TestClosuresExecutor() {
		this.callableTasks = new ArrayList<Callable<String>>()
	}

	void addClosure(Closure closure) {
		Callable<String> callableTask = {
			TimeUnit.MILLISECONDS.sleep(300)
			closure.call()
			return "Task's execution"
		}
		this.callableTasks.add(callableTask)
	}

	void execute() {
		int size = callableTasks.size()
		if (size < 1) {
			throw new IllegalStateException("should add one or more Callable objects")
		}
		ExecutorService executorService = Executors.newFixedThreadPool(size)

		List<Future<String>> futures = executorService.invokeAll(callableTasks)

		for (ft in futures) {
			String result = null
			try {
				/* calling the get() method while the task is
				 * running will cause execution to block
				 * until the task properly execut4s and
				 * the result is available
				 */
				result = ft.get()
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace()
			}
		}

		executorService.shutdown()
		try {
			if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				executorService.shutdownNow()
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow()
		}
	}
}

