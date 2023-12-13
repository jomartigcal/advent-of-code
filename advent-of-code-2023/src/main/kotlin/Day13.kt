// Day 13: Point of Incidence
// https://adventofcode.com/2023/day/13

import java.io.File
import kotlin.math.min

fun main() {
    val lines = File("src/main/resources/Day13.txt").readLines()
    val listOfList = mutableListOf(mutableListOf<String>())
    lines.forEach {
        if (it.isEmpty()) {
            listOfList.add(mutableListOf())
        } else {
            listOfList.last().add(it)
        }
    }

    val sum = listOfList.sumOf { pattern ->
        countPattern(pattern)
    }
    println(sum)

    //TODO Part 2
}

fun countPattern(pattern: List<String>): Int {
    val vertical = countVerticalPattern(pattern)
    return if (vertical > 0) {
        vertical
    } else {
        val horizontal = countHorizontalPattern(pattern)
        horizontal * 100 //if horizontal = rows * 100 above
    }
}

fun countVerticalPattern(pattern: List<String>): Int {
    val length = pattern.first().length

    for (y in 0..<length - 1) {
        val min: Int = min(y + 1, length - 1 - y)

        val hasReflection = pattern.all { pat ->
            val left = pat.subSequence(y - min + 1..y).toString()
            val right = pat.subSequence(y + 1..y + min).reversed().toString()
            left == right
        }

        if (hasReflection) return y + 1
    }

    return 0
}

fun countHorizontalPattern(pattern: List<String>): Int {
    val height = pattern.size
    for (x in 0..<height - 1) {
        val min: Int = min(x + 1, height - 1 - x)

        val top = pattern.slice(x - min + 1..x)
        val bottom = pattern.slice(x + 1..x + min).reversed()

        if (top == bottom) return x + 1
    }

    return 0
}
