/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package adventofcode

class Day03 {

    fun part1() {
        val testInput = getResourceAsStringCollection("day03_test.txt")
        val testResult = calculateSum(testInput)

        assert(testResult == 4361) { "example not passing" }

        val input = getResourceAsStringCollection("day03_input.txt")
        val result = calculateSum(input)

        println("result - $result")
    }

    fun part2() {
        val testInput = getResourceAsStringCollection("day03_test.txt")
        val testResult = calculateGearRatio(testInput)

        assert(testResult == 467835) { "example not passing" }

        val input = getResourceAsStringCollection("day03_input.txt")
        val result = calculateGearRatio(input)

        println("result - $result")
    }

    private val numberRegex = "[0-9]+".toRegex()

    private fun calculateSum(input: List<String>): Int {
        //transform into intrange(indices) collection
        val indices = input.map { line ->
            LineData(
                symbolData = line.findSymbolIndices(),
                numericData = numberRegex.findAll(line).map {
                    IndexData(it.value.toInt(), IntRange(it.range.first - 1, it.range.last + 1))
                }.toList(),
            )
        }

        return indices
            .flatMapIndexed { index: Int, lineData: LineData ->
                val controlTop = indices.getOrNull(index - 1)?.symbolData.orEmpty()
                val controlSelf = lineData.symbolData
                val controlBottom = indices.getOrNull(index + 1)?.symbolData.orEmpty()

                lineData.numericData
                    .filter { nd ->
                        controlTop.any { it in nd.range } ||
                                controlSelf.any { it in nd.range } ||
                                controlBottom.any { it in nd.range }
                    }
                    .map { it.value }
            }
            .sum()
    }

    private fun String.findSymbolIndices(): List<Int> =
        mapIndexedNotNull { index, c -> if (!c.isDigit() && c != '.') index else null }

    data class LineData(
        val symbolData: List<Int>,
        val numericData: List<IndexData>
    )

    data class IndexData(
        val value: Int,
        val range: IntRange,
    )

    private fun calculateGearRatio(input: List<String>): Int {
        return input.windowed(size = 3).map { lines ->
            val top = lines[0]
            val self = lines[1]
            val bottom = lines[2]

             self.findGearIndices().mapNotNull { gi ->
                val gn = top.findGearNumbers(gi) + self.findGearNumbers(gi) + bottom.findGearNumbers(gi)
                gn.takeIf { it.size == 2 }?.fold(1) { x, y -> x * y }
            }.sum()
        }.sum()
    }

    private fun String.findGearIndices(): List<Int> =
        mapIndexedNotNull { index, c -> if (c == '*') index else null }

    private fun String.findGearNumbers(gearIndex: Int): List<Int> =
        numberRegex.findAll(this)
            .mapNotNull { mr ->
                if (gearIndex in IntRange(mr.range.first - 1, mr.range.last + 1)) mr.value.toInt()
                else null
            }
            .toList()
}

fun main() {
    //Day03().part1()
    Day03().part2()
}