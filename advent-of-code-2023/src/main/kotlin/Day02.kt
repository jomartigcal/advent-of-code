// Day 2: Cube Conundrum
// https://adventofcode.com/2023/day/2

import java.io.File
import kotlin.math.max

private const val RED = "red"
private const val GREEN = "green"
private const val BLUE = "blue"

private const val COLON = ":"
private const val COMMA = ","
private const val SEMI_COLON = ";"

fun main() {
    val lines = File("src/main/resources/Day02.txt").readLines()
    val sumOfIds = lines.sumOf { line ->
        if (isPossible(line.substringAfter(COLON))) {
            line.substringAfter("Game ").substringBefore(COLON).toInt()
        } else {
            0
        }
    }
    println(sumOfIds)

    val powers = lines.sumOf { line ->
        getPowerSet(line.substringAfter(COLON))
    }
    println(powers)
}

private fun isPossible(line: String): Boolean {
    line.split(SEMI_COLON).forEach { draw ->
        draw.split(COMMA).forEach { cube ->
            if (cube.endsWith(RED) && cube.substringBefore(RED).trim().toInt() > 12) {
                return false
            } else if (cube.endsWith(GREEN) && cube.substringBefore(GREEN).trim().toInt() > 13) {
                return false
            } else if (cube.endsWith(BLUE) && cube.substringBefore(BLUE).trim().toInt() > 14) {
                return false
            }
        }
    }

    return true
}

private fun getPowerSet(line: String): Int {
    var redPower = 0
    var greenPower = 0
    var bluePower = 0

    line.split(SEMI_COLON).forEach { draw ->
        draw.split(COMMA).forEach { cube ->
            if (cube.endsWith(RED)) {
                redPower = max(redPower, cube.substringBefore(RED).trim().toInt())
            } else if (cube.endsWith(GREEN)) {
                greenPower = max(greenPower, cube.substringBefore(GREEN).trim().toInt())
            } else if (cube.endsWith(BLUE)) {
                bluePower = max(bluePower, cube.substringBefore(BLUE).trim().toInt())
            }
        }
    }

    return redPower * greenPower * bluePower
}
