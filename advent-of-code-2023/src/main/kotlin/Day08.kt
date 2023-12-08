// Day 08: Haunted Wasteland
// https://adventofcode.com/2023/day/8

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day08.txt").readLines()

    val steps = lines.first().toList()
    val map = lines.drop(2)

    println(getHumanSteps(map, steps) + 1)
}

private fun getHumanSteps(map: List<String>, steps: List<Char>): Int {
    val start = map.indexOfFirst { it.startsWith("AAA") }
    val end = map.indexOfFirst { it.startsWith("ZZZ") }

    var position = start
    var counter = -1
    while (position != end) {
        counter++

        val move = steps[counter % steps.size]
        val step = if (move == 'L') {
            map[position].substringAfter("(").substringBefore(", ")
        } else {
            map[position].substringAfter(", ").substringBefore(")")
        }
        position = map.indexOfFirst { it.startsWith(step) }
    }
    return counter
}