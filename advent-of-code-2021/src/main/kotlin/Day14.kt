//Day 14: Extended Polymerization
//https://adventofcode.com/2021/day/14

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day14.txt").readLines()
    val template = lines.first()
    val rules = lines.drop(2).associate {
        val (adjacentElements, insertElement) = it.split(" -> ")
        adjacentElements to insertElement
    }

    polymerize(template, rules, 10)
    polymerize(template, rules, 40)
}

fun polymerize(template: String, rules: Map<String, String>, steps: Int) {
    val list = template.windowed(2).toMutableList()
    val map = list.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()

    repeat(steps) {
        val tempMap = map.filterValues { it > 0 }
        for (key in tempMap.keys.toList()) {
            val insert = rules[key]
            val count = tempMap.getOrDefault(key, 0L)

            map[key] = map.getOrDefault(key, 0L) - count
            map["${key.first()}$insert"] = map.getOrDefault("${key.first()}$insert", 0L) + count
            map["$insert${key.last()}"] = map.getOrDefault("$insert${key.last()}", 0L) + count
        }
    }

    val letterMap = mutableMapOf<Char, Long>()
    map.onEach { item ->
        letterMap[item.key.last()] = letterMap.getOrDefault(item.key.last(), 0L) + item.value
    }
    letterMap[list.first().first()] = letterMap.getOrDefault(list.first().first(), 0L) + 1L

    println(letterMap.maxOf { it.value } - letterMap.minOf { it.value })
}
