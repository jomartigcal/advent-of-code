import java.io.File

// Day 03: Lobby
// https://adventofcode.com/2025/day/3

fun main() {
    val banks = File("src/main/resources/Day03.txt").readLines()
        .map {
            it.toList().map { it.digitToInt() }
        }

    getHighestJoltage2(banks)
}

private fun getHighestJoltage2(banks: List<List<Int>>) {
    banks.map { ratings ->
        val highest = ratings.take(ratings.size - 1).maxOf { it }

        val nextHighest = ratings.drop(ratings.indexOf(highest) + 1).maxOf { it }
        highest * 10 + nextHighest
    }.sumOf { it }.also { println(it) }
}
