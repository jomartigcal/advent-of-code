//Day 03: Rucksack Reorganization
//https://adventofcode.com/2022/day/3

import java.io.File

fun main() {
    val rucksacks = File("src/main/resources/Day03.txt").readLines()
    reorganizeRucksacks(rucksacks)
    reorganizeRucksacksByGroup(rucksacks.chunked(3))
}

private fun reorganizeRucksacks(rucksacks: List<String>) {
    val priorities = rucksacks.sumOf { items ->
        val compartment1 = items.take(items.length / 2).toSet()
        val compartment2 = items.drop(items.length / 2).toSet()
        getPriority(compartment1 intersect compartment2)
    }
    println(priorities)
}

fun reorganizeRucksacksByGroup(groups: List<List<String>>) {
    val priorities = groups.sumOf { group ->
        val common = group.first().toSet() intersect group[1].toSet() intersect group.last().toSet()
        getPriority(common)
    }
    println(priorities)
}
private fun getPriority(chars: Set<Char>): Int {
    return chars.sumOf { char ->
        if (char in 'a'..'z') char.code - 96
        else char.code - 38
    }
}

