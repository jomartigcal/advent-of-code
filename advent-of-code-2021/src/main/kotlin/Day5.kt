//Day 5: Hydrothermal Venture
//https://adventofcode.com/2021/day/5

import java.io.File

val regex = Regex("""(\d+),(\d+) -> (\d+),(\d+)""")

fun main() {
    val lines = File("src/main/resources/Day5.txt").readLines()
    val vents = lines.map {
        regex.matchEntire(it)!!
            .destructured
            .let { (x1, y1, x2, y2) ->
                (x1.toInt() to y1.toInt()) to (x2.toInt() to y2.toInt())
            }
    }

    val horizontalVents = vents.filter { it.first.first == it.second.first }
    val verticalVents = vents.filter { it.first.second == it.second.second }
    val diagonalVents = vents.toMutableList() - horizontalVents - verticalVents

    countOverlap(horizontalVents, verticalVents, diagonalVents, false)
    countOverlap(horizontalVents, verticalVents, diagonalVents, true)
}

fun countOverlap(
    horizontalVents: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
    verticalVents: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
    diagonalVents: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
    includeDiagonals: Boolean
) {
    val ventMap = mutableMapOf<Pair<Int, Int>, Int>()

    horizontalVents.forEach {
        val x = it.first.first
        val y1 = it.first.second
        val y2 = it.second.second

        val (min, max) = if (y1 > y2) y2 to y1 else y1 to y2
        (min..max).forEach { y ->
            ventMap[x to y] = ventMap.getOrDefault(x to y, 0) + 1
        }
    }

    verticalVents.forEach {
        val y = it.first.second
        val x1 = it.first.first
        val x2 = it.second.first

        val (min, max) = if (x1 > x2) x2 to x1 else x1 to x2
        (min..max).forEach { x ->
            ventMap[x to y] = ventMap.getOrDefault(x to y, 0) + 1
        }
    }

    if (includeDiagonals) {
        diagonalVents.forEach {
            val x1 = it.first.first
            val y1 = it.first.second
            val x2 = it.second.first
            val y2 = it.second.second

            if (x1 > x2) {
                (x2..x1).forEachIndexed { index, _ ->
                    val y = if (y1 > y2) y2 + index else y2 - index
                    ventMap[x2 + index to y] = ventMap.getOrDefault(x2 + index to y, 0) + 1
                }
            } else {
                (x1..x2).forEachIndexed { index, _ ->
                    val y = if (y1 > y2) y1 - index else y1 + index
                    ventMap[x1 + index to y] = ventMap.getOrDefault(x1 + index to y, 0) + 1
                }
            }
        }
    }

    println(ventMap.count { it.value >= 2 })
}
