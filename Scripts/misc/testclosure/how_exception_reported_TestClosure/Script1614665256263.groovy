import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

import com.kazurayam.ks.testclosure.TestClosure
import com.kazurayam.ks.testclosure.TestClosureCollectionExecutor
import com.kms.katalon.core.util.KeywordUtil

List<TestClosure> tclosures = new ArrayList<TestClosure>()

Closure closure1 = { Point position, Dimension dimension, String url ->
	KeywordUtil.markFailedAndStop("Failed!")
}

tclosures.add(new TestClosure(closure1, ["http://demoaut.katalon.com/"]))

TestClosureCollectionExecutor executor =
    new TestClosureCollectionExecutor.Builder().build()

executor.addTestClosures(tclosures)

executor.execute() 
