//Day 11: Dumbo Octopus
//https://adventofcode.com/2021/day/11

import java.io.File
import kotlin.math.sqrt

fun main() {
    val octopuses = mutableListOf<Octopus>()
    File("src/main/resources/Day11.txt").readLines().forEachIndexed { x, line ->
        line.toList().forEachIndexed { y, energy ->
            octopuses.add(Octopus(x to y, energy.digitToInt()))
        }
    }

    countFlashes(octopuses.map { it.copy() }, 100)
    findFlashSync(octopuses.map { it.copy() })
}

fun countFlashes(octopuses: List<Octopus>, steps: Int) {
    (1..steps).forEach { step ->
        simulateStep(octopuses, step)
    }

    val flashes = octopuses.sumOf { it.flashes() }
    println(flashes)
}

fun simulateStep(octopuses: List<Octopus>, step: Int) {
    octopuses.forEach { octopus ->
        energizeOctopus(octopuses, octopus, step)
    }
}

fun energizeOctopus(octopuses: List<Octopus>, octopus: Octopus, step: Int) {
    val edge = sqrt(octopuses.size.toDouble()).toInt()

    val energy = octopus.energize(step)
    if (energy > 9) {
        octopus.flash()
        octopus.adjacentIndexes().filter {
            it.first in 0 until edge && it.second in 0 until edge
        }.forEach { adjacent ->
            val adjacentOctopus = octopuses.find { it.index == adjacent.first to adjacent.second }
            adjacentOctopus?.let { energizeOctopus(octopuses, adjacentOctopus, step) }
        }
    }
}

fun findFlashSync(octopuses: List<Octopus>) {
    var step = 0
    while (octopuses.all { it.isFlashing() }.not()) {
        step++
        simulateStep(octopuses, step)
    }
    println(step)
}

data class Octopus(val index: Pair<Int, Int>, val initialEnergy: Int) {
    private var flash = 0
    private var energy = initialEnergy
    private var step = 0

    fun flashes() = flash

    fun energize(step: Int): Int {
        if (energy != 0) energy++

        if (energy == 0 && this.step != step) {
            energy++
        }
        this.step = step

        return energy
    }

    fun adjacentIndexes(): List<Pair<Int, Int>> {
        val x = index.first
        val y = index.second
        return listOf(
            x - 1 to y + 1,
            x to y + 1,
            x + 1 to y + 1,
            x - 1 to y,
            x + 1 to y,
            x - 1 to y - 1,
            x to y - 1,
            x + 1 to y - 1
        )
    }

    fun flash() {
        flash++
        energy = 0
    }

    fun isFlashing() = energy == 0
}
