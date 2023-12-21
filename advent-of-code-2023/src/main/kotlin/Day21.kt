// Day 21: Step Counter
// https://adventofcode.com/2023/day/21

import java.io.File

private const val PLOT = '.'
private const val START = 'S'

fun main() {
    val lines = File("src/main/resources/Day21.txt").readLines().map { it.toList() }
    var start = 0 to 0

    val plots = lines.mapIndexed { y, line ->
        line.mapIndexed { x, spot ->
            when (spot) {
                START -> {
                    start = x to y
                    x to y
                }

                PLOT -> {
                    x to y
                }

                else -> {
                    null
                }
            }
        }
    }.flatten().filterNotNull()

    takeSteps(plots, start, 64)

    //TODO Part 2
}

private fun takeSteps(plots: List<Pair<Int, Int>>, start: Pair<Int, Int>, count: Int) {
    var nextSteps = setOf(start)

    var steps = mutableSetOf<Pair<Int, Int>>()

    repeat(count) {
        steps = mutableSetOf()
        nextSteps.forEach { nextStep ->
            var plot = nextStep.first to nextStep.second + 1
            if (plot in plots && plot !in nextSteps) {
                steps.add(plot)
            }

            plot = nextStep.first to nextStep.second - 1
            if (plot in plots && plot !in nextSteps) {
                steps.add(plot)
            }

            plot = nextStep.first - 1 to nextStep.second
            if (plot in plots && plot !in nextSteps) {
                steps.add(plot)
            }

            plot = nextStep.first + 1 to nextStep.second
            if (plot in plots && plot !in nextSteps) {
                steps.add(plot)
            }
            nextSteps = steps
        }
    }

    println(steps.size)
}
