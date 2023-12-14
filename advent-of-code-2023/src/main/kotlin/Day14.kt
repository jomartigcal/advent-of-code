// Day 14: Point of Incidence
// https://adventofcode.com/2023/day/14

import java.io.File
import kotlin.math.min

private const val CUBE = "#"
private const val ROUNDED_ROCK = 'O'

fun main() {
    val rocks = File("src/main/resources/Day14.txt").readLines()
    val totalLoad = tiltRocksAndCount(rocks)
    println(totalLoad)
}

fun tiltRocksAndCount(rocks: List<String>): Int {
    var count = 0
    val width = rocks.first().length
    for (i in 0..<width) {
        count += tiltRocksByColumnAndCount(
            rocks.map { it[i] }.joinToString("")
        )
    }
    return count
}

fun tiltRocksByColumnAndCount(columnOfRocks: String): Int {
    var totalLoad = 0
    val height = columnOfRocks.length

    var index = 0
    while (index < height) {
        var i: Int
        val next = if (columnOfRocks.substring(index).contains(CUBE)) {
            i = columnOfRocks.indexOf(CUBE, index)
            min(i, height)
        } else {
            i = height
            height
        }

        val roundRocks = columnOfRocks.substring(index..<next).count { it == ROUNDED_ROCK }
        val load = roundRocks * (height - index) - (0..<roundRocks).sum()

        totalLoad += load
        index = i + 1
    }

    return totalLoad
}
