import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import misc.callable.CalculationJob

ExecutorService executorService = Executors.newFixedThreadPool(4);

Future<Integer> result = executorService.submit(new CalculationJob(1,2));
// submit() will not wait for the callable to return.

// result.get() will wait for the callable to return
try {
	Integer integer = result.get(10, TimeUnit.MILLISECONDS);
	System.out.println("result: " + integer);
	assert 3 == integer
} catch (Exception e) {
	// interrupts if there is any possible error
	result.cancel(true);
}

executorService.shutdown();
executorService.awaitTermination(1, TimeUnit.SECONDS);
