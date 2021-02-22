import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

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

