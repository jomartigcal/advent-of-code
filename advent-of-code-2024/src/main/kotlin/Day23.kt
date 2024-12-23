// Day 23: LAN Party
// https://adventofcode.com/2024/day/23

import java.io.File

fun main() {
    val connections = File("src/main/resources/Day23.txt").readLines()

    val map = mutableMapOf<String, HashSet<String>>()
    connections.forEach {
        val (x, y) = it.split("-")
        map.computeIfAbsent(x, { hashSetOf() })
            .add(y)
        map.computeIfAbsent(y, { hashSetOf() })
            .add(x)
    }

    val chiefConnections = map.keys.filter { it.startsWith("t") }.associateWith { map[it] }

    val pairs = mutableListOf<Pair<String, String>>()
    chiefConnections.forEach { con ->
        con.value?.forEach {
            pairs.add(con.key to it)
        }
    }

    pairs.map { pair ->
        val x = map.getOrDefault(pair.first, emptyList())
        val y = map.getOrDefault(pair.second, emptyList())
        (x intersect y).map {
            Triple(pair.first, pair.second, it)
        }
    }.flatten().distinctBy { it.toList().sorted() }.also { println(it.size) }
}
