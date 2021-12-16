//Day 15: Chiton
//https://adventofcode.com/2021/day/15

import java.io.File
import java.util.PriorityQueue

data class Position(val xy: Pair<Int, Int>, val risk: Int)

val queue = PriorityQueue(compareBy(Position::risk))

fun main() {
    val risks = File("src/main/resources/Day15.txt").readLines().map { positions ->
        positions.toList().map { it.digitToInt() }
    }
    val length = risks.size
    val width = risks.first().size

    val array = Array(length) { x ->
        IntArray(width) { y ->
            risks[x][y]
        }
    }
    findLowestRiskPath(array)

    val lengthX5 = 5 * length
    val widthX5 = 5 * width
    val arrayX5 = Array(lengthX5) { x ->
        IntArray(widthX5) { y ->
            val z = x / length + y / width
            ((risks[x % length][y % width] + z - 1) % 9) + 1
        }
    }

    findLowestRiskPath(arrayX5)
}

fun findLowestRiskPath(array: Array<IntArray>) {
    val length = array.size
    val width = array.first().size
    val delta = Array(length) { IntArray(width) { Int.MAX_VALUE } }
    val visited = Array(length) { BooleanArray(width) }

    delta[0][0] = 0
    queue.add(Position(0 to 0, 0))
    while (!visited[length - 1][width - 1]) {
        val (xy, risk) = queue.remove()
        val (x, y) = xy
        if (visited[x][y]) continue
        visited[x][y] = true

        edgeRelax(array, delta, visited, x - 1, y, risk)
        edgeRelax(array, delta, visited, x + 1, y, risk)
        edgeRelax(array, delta, visited, x, y - 1, risk)
        edgeRelax(array, delta, visited, x, y + 1, risk)
    }

    println(delta[length - 1][width - 1])
}

fun edgeRelax(array: Array<IntArray>, delta: Array<IntArray>, visited: Array<BooleanArray>, x: Int, y: Int, risk: Int) {
    val length = array.size
    val width = array.first().size

    if (x !in 0 until length || y !in 0 until width || visited[x][y]) return

    val newRisk = risk + array[x][y]
    if (newRisk < delta[x][y]) {
        delta[x][y] = newRisk
        queue += Position(x to y, newRisk)
    }
}
