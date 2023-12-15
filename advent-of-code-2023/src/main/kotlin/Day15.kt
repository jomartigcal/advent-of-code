// Day 15: Lens Library
// https://adventofcode.com/2023/day/15

import java.io.File

fun main() {
    val input = File("src/main/resources/Day15.txt").readText()
    val initializationSequence = input.split(",")

    println(initializationSequence)
    val initializationSequenceSum = initializationSequence.sumOf { step ->
        println(step)
        hashStepString(step.toList())
    }
    println(initializationSequenceSum)

    val sumOfFocusPower = initializationSequence.sumOf { step ->
        findFocusPowerOf(step)
    }
}

private fun hashStepString(step: List<Char>): Int {
    var currentValue = 0

    for (character in step) {
        currentValue += character.code
        currentValue *= 17
        currentValue %= 256
    }

    return currentValue
}

fun findFocusPowerOf(step: String): Int {
    //TODO Part 2

    return 0
}
