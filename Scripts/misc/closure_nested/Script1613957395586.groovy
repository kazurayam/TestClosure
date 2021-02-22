/**
 * Closures in Groovy, Baeldung
 * https://www.baeldung.com/groovy-closures
 */
def calculate = { int x, int y, String operation ->
	def log = {
		println "performing ${it}"
	}
	
	def result = 0
	switch (operation) {
		case 'ADD':
		    log("Addition")
			result = x + y
			break
		case 'SUB':
			log("Subtraction")
			result = x - y
			break
		case 'MUL':
			log("Multiplication")
			result = x * y
			break
		case 'DIV':
			log("Division")
			result = x / y
			break
	}
	return result
}
assert calculate(6, 3, "ADD")
assert calculate(6, 3, "SUB")
assert calculate(6, 3, "MUL")
assert calculate(6, 3, "DIV")
