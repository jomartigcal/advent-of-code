// Day 1: Trebuchet?!
// https://adventofcode.com/2023/day/1

import java.io.File

private val digits = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

fun main() {
    val lines = File("src/main/resources/Day01.txt").readLines()
    val sum1 = lines.sumOf { line ->
        line.first { it.isDigit() }.digitToInt() * 10 + line.last { it.isDigit() }.digitToInt()
    }
    println(sum1)

    val sum2 = lines.sumOf { line ->
        getFirstDigit(line) * 10 + getLastDigit(line)
    }
    println(sum2)
}

private fun getFirstDigit(line: String): Int {
    var temp = line
    line.toList().map { it.toString() }.runningReduce { acc, string -> acc + string }.forEach {
        val stringToReplace = stringToReplace(it)
        if (stringToReplace.isNotEmpty()) {
            temp = temp.replace(stringToReplace, digits.indexOf(stringToReplace).toString())
        }
    }
    return temp.first { it.isDigit() }.digitToInt()
}

private fun getLastDigit(line: String): Int {
    var temp = line
    line.reversed().toList().map { it.toString() }.runningReduce { acc, string -> acc + string }.forEach {
        val stringToReplace = stringToReplace(it.reversed())
        if (stringToReplace.isNotEmpty()) {
            temp = temp.replace(stringToReplace, digits.indexOf(stringToReplace).toString())
        }
    }
    return temp.last { it.isDigit() }.digitToInt()
}

private fun stringToReplace(line: String): String {
    digits.forEachIndexed { _, digit ->
        if (line.contains(digit)) {
            return digit
        }
    }

    return ""
}