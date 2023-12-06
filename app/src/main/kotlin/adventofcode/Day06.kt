/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package adventofcode

import java.lang.IllegalArgumentException

class Day06 {

    fun part1() {
        println("=== Part 1 ===")
        val testInput = getResourceAsStringCollection("day06_test.txt")
        val testResult = getPart1(testInput)
        println("test result - $testResult")
        if (testResult != 288) throw Exception("example not passing")

        val input = getResourceAsStringCollection("day06_input.txt")
        val result = getPart1(input)
        println("result - $result")
    }

    fun part2() {
        println("=== Part 2 ===")
        val testInput = getResourceAsStringCollection("day06_test.txt")
        val testResult = getPart2(testInput)
        println("test result - $testResult")
        if (testResult != 71503) throw Exception("example not passing")

        val input = getResourceAsStringCollection("day06_input.txt")
        val result = getPart2(input)
        println("result - $result")
    }

    private fun getPart1(input: List<String>): Int {
        val races = getRaces(input)

        var i = 0
        races.forEach { r ->
            val waysToWin = (0..r.time)
                .filter { rt -> (r.time - rt) * rt > r.record }
                .count()
            println("$r ways=$waysToWin")
            if (waysToWin != 0) {
                i = if (i == 0) waysToWin else i * waysToWin
            }
        }

        return i
    }

    private fun getPart2(input: List<String>): Int {
        val race = getSingleRace(input)

        val waysToWin = (0..race.time.toLong())
            .filter { rt -> (race.time - rt) * rt > race.record }
            .count()
        println("$race ways=$waysToWin")

        return waysToWin
    }

    private fun getRaces(input: List<String>): List<Race> {
        val time = input[0]
            .substringAfter(":")
            .split("\\s+".toRegex())
            .filter { it.isNotEmpty() }
            .map { it.toInt() }

        val distance = input[1]
            .substringAfter(":")
            .split("\\s+".toRegex())
            .filter { it.isNotEmpty() }
            .map { it.toLong() }

        if (time.size != distance.size) throw IllegalArgumentException("Input size not matching")

        return time.mapIndexed { index, t -> Race(t, distance[index]) }
    }

    private fun getSingleRace(input: List<String>): Race {
        val time = input[0]
            .substringAfter(":")
            .filter { it.isDigit() }
            .toInt()

        val distance = input[1]
            .substringAfter(":")
            .filter { it.isDigit() }
            .toLong()

        return Race(time, distance)
    }

    data class Race(val time: Int, val record: Long)
}

fun main() {
    Day06().apply {
        part1()
        part2()
    }
}
