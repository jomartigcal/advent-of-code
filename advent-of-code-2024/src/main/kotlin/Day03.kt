// Day 03: Mull It Over
// https://adventofcode.com/2024/day/3

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day03.txt").readLines()

    val regex = """mul\((\d*),(\d*)\)""".toRegex()

    lines.sumOf { line ->
        regex.findAll(line).sumOf {
            val (multiplicand, multiplier) = it.destructured
            multiplicand.toInt() * multiplier.toInt()
        }
    }.also { println(it) }
}
