/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package adventofcode

import kotlin.math.pow

class Day04 {

    fun part1() {
        val testInput = getResourceAsStringCollection("day04_test.txt")
        val testResult = calculateSum(testInput)
        
        if (testResult != 13) throw Exception("example not passing")

        val input = getResourceAsStringCollection("day04_input.txt")
        val result = calculateSum(input)


        println("part 1 result - $result")
    }

    fun part2() {
        val testInput = getResourceAsStringCollection("day04_test.txt")
        val testResult = calculateSum2(testInput)

        println("test result - $testResult")
        if (testResult != 30) throw Exception("example not passing")

        val input = getResourceAsStringCollection("day04_input.txt")
        val result = calculateSum2(input)


        println("part 2 result - $result")
    }

    private fun calculateSum(input: List<String>): Int {
        return input.sumOf { line ->
            val numbers = line.substringAfter(":")

            val winning = numbers.substringBefore("|").trim()
                .split("\\s+".toRegex()).distinct()
            val my = numbers.substringAfter("|").trim()
                .split("\\s+".toRegex()).distinct()

            var power: Int = 0
            my.forEach { if (winning.contains(it)) power++ }
            val myWin = my.filter { winning.contains(it) }

            if (power > 0) 2.toDouble().pow(power - 1).toInt() else 0
        }
    }

    private fun calculateSum2(input: List<String>): Int {
        val cardCounts = mutableMapOf<Int, Int>()

        val cards = input.map { line ->
            CardInfo(
                id = line.substringBefore(":").filter { it.isDigit() }.toInt(),
                winning = line.substringAfter(":").substringBefore("|").trim().split("\\s+".toRegex()).distinct(),
                my = line.substringAfter(":").substringAfter("|").trim().split("\\s+".toRegex()).distinct(),
            )
        }

        cards.forEach { card ->
            val num = cardCounts.getOrPut(card.id) { 0 }
            val newNum = num + 1
            cardCounts[card.id] = newNum
            
            if (card.winningCount > 0) {
                (card.id + 1..card.id+card.winningCount).forEach {
                    cardCounts[it] = cardCounts.getOrDefault(it, 0) + newNum
                }
            }
        }

        return cardCounts.values.sum()
    }

    data class CardInfo(
        val id: Int,
        val winning: List<String>,
        val my: List<String>,
    ) {
        val winningCount: Int = my.count { winning.contains(it) }
    }
}

fun main() {
    Day04().apply {
        part1()
        part2()
    }
}