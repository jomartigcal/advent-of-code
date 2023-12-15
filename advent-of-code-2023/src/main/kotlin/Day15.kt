// Day 15: Lens Library
// https://adventofcode.com/2023/day/15

import java.io.File

private const val DASH = "-"
private const val EQUALS = "="

fun main() {
    val input = File("src/main/resources/Day15.txt").readText()
    val initializationSequence = input.split(",")

    val initializationSequenceSum = initializationSequence.sumOf { step ->
        hashString(step.toList())
    }
    println(initializationSequenceSum)

    val sumOfFocusPower = findSumOfFocusPowers(initializationSequence)
    println(sumOfFocusPower)
}

private fun hashString(string: List<Char>): Int {
    var currentValue = 0

    for (character in string) {
        currentValue += character.code
        currentValue *= 17
        currentValue %= 256
    }

    return currentValue
}

fun findSumOfFocusPowers(initializationSequence: List<String>): Int {
    val boxToLensMap = mutableMapOf<Int, List<Pair<String, Int>>>()

    initializationSequence.forEach { step ->
        val label = step.substringBefore(
            if (step.contains(EQUALS)) EQUALS else DASH
        )
        val stepBox = hashString(label.toList())
        val boxWithExistingLens = boxToLensMap.filterValues { lens ->
            lens.count { it.first == label } > 0
        }.keys

        if (step.contains(EQUALS)) {
            val focalLength = step.substringAfter(EQUALS).toInt()
            if (boxWithExistingLens.isNotEmpty()) {
                val lens = boxToLensMap.getOrDefault(stepBox, emptyList()).toMutableList()
                val indexToReplace = lens.indexOfFirst { it.first == label }
                lens[indexToReplace] = label to focalLength
                boxToLensMap[stepBox] = lens
            } else {
                boxToLensMap[stepBox] = boxToLensMap.getOrDefault(stepBox, emptyList())
                    .plus(label to focalLength)
            }
        } else if (step.contains(DASH)) {
            if (boxWithExistingLens.isNotEmpty()) {
                val lens = boxToLensMap.getOrDefault(stepBox, emptyList()).toMutableList()
                lens.removeAt(lens.indexOfFirst { it.first == label })
                boxToLensMap[stepBox] = lens
            }
        }
    }

    var sum = 0
    for (box in boxToLensMap.keys) {
        boxToLensMap[box]?.forEachIndexed { i, lens ->
            sum += (box + 1) * (i + 1) * lens.second
        }
    }

    return sum
}
