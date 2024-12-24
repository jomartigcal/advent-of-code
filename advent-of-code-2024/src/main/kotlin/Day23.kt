// Day 23: LAN Party
// https://adventofcode.com/2024/day/23

import java.io.File

fun main() {
    val map = mutableMapOf<String, HashSet<String>>()

    val connections = File("src/main/resources/Day23.txt").readLines()
    connections.forEach {
        val (x, y) = it.split("-")
        map.getOrPut(x) { hashSetOf() }
            .add(y)
        map.getOrPut(y) { hashSetOf() }
            .add(x)
    }

    val pairs = mutableListOf<Pair<String, String>>()
    map.forEach { con ->
        con.value.forEach {
            pairs.add(con.key to it)
        }
    }

    pairs
        .filter { it.first.startsWith("t") || it.second.startsWith("t") }
        .map { pair ->
            val x = map.getOrDefault(pair.first, emptyList()).toSet()
            val y = map.getOrDefault(pair.second, emptyList()).toSet()
            (x intersect y).map {
                Triple(pair.first, pair.second, it)
            }
        }
        .flatten()
        .distinctBy { it.toList().sorted() }
        .also { println(it.size) }

    pairs
        .map { pair ->
            val x = map.getOrDefault(pair.first, emptyList()).toSet()
            val y = map.getOrDefault(pair.second, emptyList()).toSet()
            buildList {
                add(pair.first)
                add(pair.second)
                addAll(x intersect y)
            }.sorted()
        }
        .groupingBy { it }
        .eachCount()
        .maxBy { it.value }.key
        .also {
            println(it.joinToString(","))
        }
}
