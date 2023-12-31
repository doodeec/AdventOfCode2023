/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package adventofcode

class Day01 {
    fun run() {
        val resourceName = "day01_input.txt"

        val input = this::class.java.classLoader.getResourceAsStream(resourceName)
            ?.bufferedReader()
            ?.readLines()
            ?: emptyList()

        //val result = sumLinesByDigits(input)
        val result = sumLinesByDigitsAndTextDigits(input)
        println("result - $result")
    }

    /**
     * Part 1
     */
    private fun sumLinesByDigits(input: List<String>): Int {
        return input
            .asSequence()
            .map { line -> line.countCombinedNumbers() }
            .sum()
    }

    /**
     * Part 2
     */
    private fun sumLinesByDigitsAndTextDigits(input: List<String>): Int {
        return input
            .asSequence()
            .map { line ->
                val result = line.getLineCount()
                println("$line - $result")
                return@map result
            }
            .sum()
    }

    private fun String.countCombinedNumbers(): Int {
        val firstDigit = firstOrNull { it.isDigit() }?.digitToInt()
        if (firstDigit == null) return 0

        val lastDigit = lastOrNull { it.isDigit() }?.digitToInt()
        if (lastDigit == null) return 0

        return firstDigit * 10 + lastDigit
    }

    private val textToDigitMap = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )
    
    private val textNumberRegex = "(${textToDigitMap.keys.joinToString(separator = "|")})".toRegex()
    
    private fun String.getLineCount(): Int {
        val first = getFirstNumber()
        if (first == 0) return 0
        
        val last = getLastNumber()
        if (last == 0) return 0
        
        return first * 10 + last
    }
    
    private fun String.getFirstNumber(): Int {
        var i = 0
        var test = ""
        while (i < length) {
            val x = this[i]
            if (x.isDigit()) return x.digitToInt()
            
            test += x
            //println("test '$test'")
            val match = textNumberRegex.find(test)
            if (match != null) return textToDigitMap[match.value]!!.toInt()
            
            i++
        }
        
        return 0
    }
    
    private fun String.getLastNumber(): Int {
        var i = length - 1
        var test = ""
        while (i >= 0) {
            val x = this[i]
            if (x.isDigit()) return x.digitToInt()

            test = x + test
            //println("test '$test'")
            val match = textNumberRegex.find(test)
            if (match != null) return textToDigitMap[match.value]!!.toInt()

            i--
        }

        return 0
    }
}

fun main() {
    Day01().run()
}
