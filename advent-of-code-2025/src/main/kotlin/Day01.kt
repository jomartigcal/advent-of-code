import java.io.File
import kotlin.math.abs

// Day 01:Secret Entrance
// https://adventofcode.com/2025/day/1

private const val COUNT = 100
private const val START_INDEX = 50

fun main() {
    val rotations = File("src/main/resources/Day01.txt").readLines().map {
        val turn = it.first()
        val count = it.drop(1).toInt()
        turn to count
    }

    turnDial(rotations)
    countZeroClicks(rotations)
}

private fun turnDial(rotations: List<Pair<Char, Int>>) {
    var index = START_INDEX
    rotations.map { rotation ->
        index = if (isLeftTurn(rotation)) {
            val diff = (index - rotation.second) % COUNT
            if (diff < 0) {
                abs(COUNT + diff)
            } else {
                diff
            }
        } else {
            (index + rotation.second) % COUNT
        }
        index
    }.count {
        it == 0
    }.also {
        println(it)
    }
}

private fun countZeroClicks(rotations: List<Pair<Char, Int>>) {
    var index = START_INDEX
    rotations.map { rotation ->
        var zeroClicks: Int
        if (isLeftTurn(rotation)) {
            zeroClicks = (rotation.second - index) / COUNT
            if (rotation.second >= index && index != 0) zeroClicks++

            val diff = (index - rotation.second) % COUNT
            index = if (diff < 0) {
                abs(COUNT + diff)
            } else {
                diff
            }
        } else {
            val oldIndex = index
            index = (oldIndex + rotation.second) % COUNT
            zeroClicks = (oldIndex + rotation.second) / COUNT
        }
        zeroClicks
    }.sumOf {
        it
    }.also { println(it) }
}

private fun isLeftTurn(rotation: Pair<Char, Int>): Boolean = rotation.first == 'L'
