//Day 1: Sonar Sweep
//https://adventofcode.com/2021/day/1

import java.io.File

fun main() {
    val list = File("src/main/resources/Day1.txt").readLines().map { it.toInt() }
    part1(list)
    part2(list)
}

fun part1(list: List<Int>) {
    println(list.zipWithNext { x, y -> y - x }.count { it > 0 })
}

fun part2(list: List<Int>) {
    part1(list.windowed(3, 1).map { it.sum() })
}
