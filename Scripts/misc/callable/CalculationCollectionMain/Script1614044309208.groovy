import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import misc.callable.CalculationJob

ExecutorService executorService = Executors.newFixedThreadPool(4)

List<Callable<Integer>> callables = new ArrayList<Callable<Integer>>()

callables.add(new CalculationJob(1, 2))
callables.add(new CalculationJob(10, 20))
callables.add(new CalculationJob(100, 200))

List<Future<Integer>> results = executorService.invokeAll(callables)  
// invokeAll() will block untile all Callables to finish

results.each { result ->
	try {
		Integer integer = result.get(10, TimeUnit.MILLISECONDS)
		System.out.println("result:" + integer)
	} catch (Exception e) {
		result.cancel(true)
	}
}

executorService.shutdown()
executorService.awaitTermination(1, TimeUnit.SECONDS)
