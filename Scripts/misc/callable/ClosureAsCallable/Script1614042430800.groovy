import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future


ExecutorService executor = Executors.newFixedThreadPool(5)

Future future = executor.submit((Callable) { return 'Hello' })
println future.get()
assert 'Hello' == future.get()

//
future = executor.submit({ return "Good-bye" } as Callable)
println future.get()
assert "Good-bye" == future.get()