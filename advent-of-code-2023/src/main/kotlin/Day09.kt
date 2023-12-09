// Day 09: Mirage Maintenance
// https://adventofcode.com/2023/day/9

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day09.txt").readLines()
    val listOfValues = lines.map { line ->
        line.split(" ").map { it.toInt() }
    }

    val sumOfExtrapolatedForward = listOfValues.sumOf { values ->
        extrapolateForward(values)
    }
    println(sumOfExtrapolatedForward)

    //TODO Part 2
}

private fun extrapolateForward(values: List<Int>): Int {
    var mutableList = values.toMutableList()
    var counter = mutableList.last()

    while (true) {
        mutableList = mutableList.zipWithNext().map { it.second-it.first }.toMutableList()
        if (mutableList.distinct().size == 1) {
            return counter+mutableList.last()
        }

        counter += mutableList.last()
    }
}