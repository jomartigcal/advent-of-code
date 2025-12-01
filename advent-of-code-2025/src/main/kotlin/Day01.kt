import java.io.File
import kotlin.math.abs

// Day 01:Secret Entrance
// https://adventofcode.com/2025/day/1

private const val COUNT = 100

fun main() {
    val rotations = File("src/main/resources/Day01.txt").readLines().map {
        val turn = it.first()
        val count = it.drop(1).toInt()
        turn to count
    }

    turnDial(rotations)
}

private fun turnDial(rotations: List<Pair<Char, Int>>) {
    var index = 50
    rotations.map {
        index = turnDial(value = index, count = it.second, left = it.first == 'L')
        index
    }.count {
        it == 0
    }.also {
        println(it)
    }
}

private fun turnDial(value: Int, count: Int, left: Boolean): Int {
    return if (left) {
        val diff = (value - count) % COUNT
        if (diff < 0) {
            abs(COUNT + diff)
        } else {
            diff
        }
    } else {
        (value + count) % COUNT
    }
}
