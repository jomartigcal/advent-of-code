// Day 24: Never Tell Me The Odds
// https://adventofcode.com/2023/day/24

import java.io.File

data class Hailstone(
    val initialPosition: Triple<Long, Long, Long>,
    val velocity: Triple<Long, Long, Long>,
) {
    private fun getSlope(): Double {
        return velocity.second / velocity.first.toDouble()
    }

    private fun getIntercept(): Double {
        return initialPosition.second - (getSlope() * initialPosition.first)
    }

    fun extrapolateY(x: Double): Double {
        return getSlope() * x + getIntercept()
    }
}

fun main() {
    val lines = File("src/main/resources/Day24.txt").readLines()

    val regex = """(-? ?\d*), (-? ?\d*), (-? ?\d*) @ (-? ?\d+), (-? ?\d+), (-? ?\d+)""".toRegex()

    val hailstones = lines.mapNotNull {
        regex.matchEntire(it)?.destructured?.let { result ->
            val (px, py, pz, vx, vy, vz) = result
            Hailstone(
                initialPosition = Triple(px.trim().toLong(), py.trim().toLong(), pz.trim().toLong()),
                velocity = Triple(vx.trim().toLong(), vy.trim().toLong(), vz.trim().toLong())
            )
        }
    }

    val delta1 = 200_000_000_000_000.0
    val delta2 = 400_000_000_000_000.0
    checkIfHailstonesCross(hailstones, delta1, delta2)

    //TODO Part 2
}

private fun checkIfHailstonesCross(hailstones: List<Hailstone>, delta1: Double, delta2: Double) {
    val pathsToCheck = hailstones.mapIndexed { x, hailstone ->
        (x + 1..<hailstones.size).map { y ->
            hailstone to hailstones[y]
        }
    }.flatten()

    val intersections = pathsToCheck.count { (hailstone1, hailstone2) ->
        val x1 = delta1
        val x2 = delta2
        val y1 = hailstone1.extrapolateY(x1)
        val y2 = hailstone1.extrapolateY(x2)

        val x3 = delta1
        val x4 = delta2
        val y3 = hailstone2.extrapolateY(x3)
        val y4 = hailstone2.extrapolateY(x4)

        val x12 = (x1 - x2)
        val x34 = (x3 - x4)
        val y12 = (y1 - y2)
        val y34 = (y3 - y4)

        val c = (x12 * y34 - y12 * x34).toFloat()
        val a = (x1 * y2 - y1 * x2).toFloat()
        val b = (x3 * y4 - y3 * x4).toFloat()

        val x = (a * x34 - b * x12) / c
        val y = (a * y34 - b * y12) / c

        val inAreaIntersect = x in delta1..delta2 && y in delta1..delta2

        val isIntersectFutureOfH1 = hailstone1.velocity.first < 0 && x < hailstone1.initialPosition.first ||
                hailstone1.velocity.first > 0 && x > hailstone1.initialPosition.first
        val isIntersectFutureOfH2 = hailstone2.velocity.first < 0 && x < hailstone2.initialPosition.first ||
                hailstone2.velocity.first > 0 && x > hailstone2.initialPosition.first

        inAreaIntersect && isIntersectFutureOfH1 && isIntersectFutureOfH2
    }
    println(intersections)
}
