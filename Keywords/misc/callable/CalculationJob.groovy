package misc.callable

import java.util.concurrent.Callable

public class CalculationJob implements Callable<Integer> {

	int x;
	int y;

	public CalculationJob(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Integer call() throws Exception {
		return x + y;
	}
}
