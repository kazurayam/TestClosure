package solution

import java.util.concurrent.Callable

class URLVisitor implements Callable<String> {
	private final Closure closure
	private final String url
	URLVisitor(Closure closure, String url) {
		this.closure = closure
		this.url = url
	}
	@Override
	String call() throws Exception {
		closure.call(url)
		return "OK"
	}
}
