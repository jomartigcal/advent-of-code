// Day 06: Wait For It
// https://adventofcode.com/2023/day/6

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day06.txt").readLines()
    val times = lines.first().substringAfter("Time:").split(" ").mapNotNull {
        it.toLongOrNull()
    }
    val distances = lines.last().substringAfter("Distance:").split(" ").mapNotNull {
        it.toLongOrNull()
    }

    var waysToWin = 1
    for (i in times.indices) {
        waysToWin *= countWaysToWin(times[i], distances[i])
    }
    println(waysToWin)

    waysToWin = countWaysToWin(
        times.joinToString("").toLong(),
        distances.joinToString("").toLong()
    )
    println(waysToWin)
}

private fun countWaysToWin(time: Long, distance: Long): Int {
    return (0..time).count { hold ->
        val travel = hold * (time - hold)
        travel > distance
    }
}
