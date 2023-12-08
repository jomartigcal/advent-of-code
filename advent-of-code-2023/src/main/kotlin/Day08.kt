// Day 08: Haunted Wasteland
// https://adventofcode.com/2023/day/8

import java.io.File
import kotlin.math.min

fun main() {
    val lines = File("src/main/resources/Day08.txt").readLines()

    val steps = lines.first().toList()
    val map = lines.drop(2)

    println(getHumanSteps(map, steps))
    println(getGhostSteps(map, steps))
}

private fun getHumanSteps(map: List<String>, steps: List<Char>): Int {
    val start = map.indexOfFirst { it.startsWith("AAA") }
    val end = map.indexOfFirst { it.startsWith("ZZZ") }

    var position = start
    var counter = -1
    while (position != end) {
        counter++

        val move = steps[counter % steps.size]
        val step = getNextPath(map[position], move)
        position = map.indexOfFirst { it.startsWith(step) }
    }
    return counter + 1
}

private fun getGhostSteps(map: List<String>, steps: List<Char>): Long {
    val starts = map.filter { it[2] == 'A' }

    val counts = starts.map { start ->
        var counter = 0L
        var index = 0
        var step = start

        while (step[2] != 'Z') {
            val move = steps[index]
            val next = getNextPath(step, move)
            step = map.first { it.startsWith(next) }

            index = (index + 1) % steps.size
            counter++
        }
        counter
    }

    return counts.reduce { acc, i ->
        lcm(acc, i)
    }
}

private fun getNextPath(string: String, move: Char): String {
    return if (move == 'L') {
        string.substringAfter("(").substringBefore(", ")
    } else {
        string.substringAfter(", ").substringBefore(")")
    }
}

private fun lcm(num1: Long, num2: Long): Long {
    var lcm = min(num1, num2)
    while (lcm <= num1 * num2) {
        if (lcm % num1 == 0L && lcm % num2 == 0L) {
            return lcm
        }
        lcm += min(num1, num2)
    }

    return lcm
}