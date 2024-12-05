// Day 05: Print Queue
// https://adventofcode.com/2024/day/5

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day05.txt").readLines()
    val rules = lines.takeWhile { it.isNotBlank() }.map {
        val pages = it.split("|")
        pages.first().toInt() to pages.last().toInt()
    }

    val rulesMap = mutableMapOf<Int, List<Int>>()
    rules.forEach {
        rulesMap[it.first] = rulesMap.getOrDefault(it.first, emptyList()).toMutableList().apply {
            addLast(it.second)
        }
    }

    val updates = lines.drop(rules.size + 1).map { update ->
        update.split(",").map { it.toInt() }
    }

    fun isOrderedCorrectly(pages: List<Int>): Boolean {
        pages.zipWithNext().forEach {
            if (rulesMap.getOrDefault(it.first, emptyList()).contains(it.second).not()) {
                return false
            }
        }
        return true
    }

    updates.sumOf { update ->
        if (isOrderedCorrectly(update)) {
            update.get(update.size / 2)
        } else {
            0
        }
    }.also(::println)
}
