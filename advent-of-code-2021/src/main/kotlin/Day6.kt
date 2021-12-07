//Day 6: Lanternfish
//https://adventofcode.com/2021/day/6

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day6.txt").readLines()
    val lanternFishes = lines.first().split(",").map { it.toInt() }
    count(lanternFishes, 80)
    count(lanternFishes, 256)
}

fun count(lanternfishes: List<Int>, days: Int) {
    val map = lanternfishes.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()
    for (day in 1..days) {
        val zero = map.getOrDefault(0, 0)
        for (x in 0..7) {
            map[x] = map.getOrDefault(x + 1, 0)
        }
        map[8] = zero
        map[6] = map.getOrDefault(6, 0) + zero
    }
    println(map.values.sum())
}
