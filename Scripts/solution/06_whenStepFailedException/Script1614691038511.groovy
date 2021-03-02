import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kms.katalon.core.util.KeywordUtil

// load the collection of TestClosures
List<TestClosure> tclosures = new ArrayList<TestClosure>()

Closure closure = { Point position, Dimension dimension, String url ->
	KeywordUtil.markFailedAndStop("Planned exception")
}

tclosures.add(new TestClosure(closure, ["http://demoaut.katalon.com/"]))

// create the executor
TestClosureCollectionExecutor executor = new TestClosureCollectionExecutor.Builder().build()

// setup the executor what to do
executor.addTestClosures(tclosures)
	
// now do the job
executor.execute()