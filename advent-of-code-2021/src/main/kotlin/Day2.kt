//Day 2: Dive!
//https://adventofcode.com/2021/day/2

import java.io.File

fun main() {
    val list = File("src/main/resources/Day2.txt").readLines()
    part1(list)
    part2(list)
}

fun part1(list: List<String>) {
    var horizontalPosition = 0
    var depth = 0

    list.forEach {
        val (command, count) = it.split(" ")
        when (command) {
            "forward" -> horizontalPosition += count.toInt()
            "down" -> depth += count.toInt()
            "up" -> depth -= count.toInt()
        }
    }

    println(horizontalPosition * depth)
}

fun part2(list: List<String>) {
    var horizontalPosition = 0
    var depth = 0
    var aim = 0

    list.forEach {
        val (command, count) = it.split(" ")
        when (command) {
            "forward" -> {
                horizontalPosition += count.toInt()
                depth += aim*count.toInt()
            }
            "down" -> aim += count.toInt()
            "up" -> aim -= count.toInt()
        }
    }

    println(horizontalPosition * depth)
}
