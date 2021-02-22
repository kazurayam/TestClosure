/**
 * Closures in Groovy, Baeldung
 * https://www.baeldung.com/groovy-closures
 */

def printWelcome = {
	println "Welcome to Closures!"
}
printWelcome()

def print = { name ->
	println name
}
print("Ramen")

print.call("Udon")

def greet = {
	return "Hello, ${it}!"
}
println greet("バナナ")

def multiply = { x, y ->
	return x * y
}
n = multiply(2, 4)
print n
assert n == 8

def calculate = { int x, int y, String operation ->
	def result = 0
	switch(operation) {
		case "ADD":
			result = x + y
			break
		case "SUB":
			result = x - y
			break
		case "MUL":
			result = x * y
			break
		case "DIV":
			result = x / y
			break
	}
	return result
}
assert calculate(12, 4, "ADD") == 16
assert calculate(43, 8, "DIV") == 5.375
println calculate(43, 8, "DIV")

def addAll = { int... args ->
	return args.sum()
}
n = addAll(12, 10, 14)
assert n == 36
println n




// 5. A Closure as an Argument
def volume(Closure areaCalculator, int... dimensions) {
	if (dimensions.size() == 3) {
		return areaCalculator(dimensions[0], dimensions[1]) * dimensions[2]
	} else if (dimensions.size() == 2) {
		return areaCalculator(dimensions[0]) * dimensions[1]
	} else if (dimensions.size() == 1) {
		return areaCalculator(dimensions[0]) * dimensions[0]
	}
}

assert volume({ l, b ->
	return l * b
}, 12, 6, 10) == 720

// Let's find a voluem of a cone using the same method
/*
assert volume({ radius, height ->
	return Math.PI * radius * radius * height / 3
}, 5, 10) == Math.PI * 250
 */
