/**
 * https://www.baeldung.com/groovy-closures
 */
def list = [10, 11, 12, 13, 14, true, false, "BUNTHER"]

list.each {
	println it
}

assert [13, 14] == list.findAll { it instanceof Integer && it >= 13 }



def map = [1:10, 2:30, 4:5]
assert [10, 60, 20] == map.collect { it.key * it.value }