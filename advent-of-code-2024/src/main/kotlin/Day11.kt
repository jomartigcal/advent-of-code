// Day 11: Plutonian Pebbles
// https://adventofcode.com/2024/day/11

import java.io.File

fun main() {
    val stones = File("src/main/resources/Day11.txt").readLines()
        .single().split(" ").map { it.toLong() }

    blink(stones, 25)
}

private fun blink(stones: List<Long>, count: Int) {
    var list = stones.toMutableList()
    repeat(count) {
        val temp = mutableListOf<Long>()
        list.forEach { stone ->
            when {
                stone == 0L -> temp.add(1)
                (stone.toString().length) % 2 == 0 -> {
                    val half1 = stone.toString().substring(startIndex = 0, endIndex = stone.toString().length / 2)
                    val half2 = stone.toString().substringAfter(half1)
                    temp.add(half1.toLong())
                    temp.add(half2.toLong())
                }

                else -> temp.add(stone * 2024)
            }
        }
        list = temp
    }
    println(list.size)
}
