//Day 7: The Treachery of Whales
//https://adventofcode.com/2021/day/7

import java.io.File
import kotlin.math.abs

fun main() {
    val positions = File("src/main/resources/Day7.txt").readLines().first()
        .split(",").map { it.toInt() }
    findPositionWithLeastFuel(positions)
}

fun findPositionWithLeastFuel(positions: List<Int>) {
    val min = positions.minOrNull()!!
    val max = positions.maxOrNull()!!

    val singleFuel = (min..max).minOf { position ->
        positions.sumOf { abs(it - position) }
    }
    println(singleFuel)

    val linearFuel = (min..max).minOf { position ->
        positions.sumOf {
            val diff = abs(it - position)
            (diff * (diff + 1)) / 2
        }
    }
    println(linearFuel)
}
