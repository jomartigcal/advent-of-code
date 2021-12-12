//Day 12: Passage Pathing
//https://adventofcode.com/2021/day/12

import java.io.File

private const val START = "start"

val connections = mutableMapOf<String, MutableList<String>>()

fun main() {
    File("src/main/resources/Day12.txt").readLines().sorted().map { connection ->
        val (cave1, cave2) = connection.split("-")
        connections[cave1] = connections.getOrDefault(cave1, mutableListOf()).also { it.add(cave2) }
        connections[cave2] = connections.getOrDefault(cave2, mutableListOf()).also { it.add(cave1) }
    }

    val paths = mutableSetOf<List<String>>()
    findPaths(listOf(START), paths) { currentPath, cave -> isCaveBig(cave) || cave !in currentPath }
    println(paths.size)

    paths.clear()
    findPaths(listOf(START), paths) { currentPath, cave ->
        isCaveBig(cave) || (cave !in currentPath)
                || (cave != START &&
                currentPath.filter { isCaveBig(it).not() }.groupingBy { it }.eachCount().containsValue(2).not())
    }
    println(paths.size)
}

fun findPaths(currentPath: List<String>, paths: MutableSet<List<String>>, check: (List<String>, String) -> Boolean) {
    if (currentPath.last() == "end") {
        paths.add(currentPath)
        return
    }

    connections[currentPath.last()]?.filter { check(currentPath, it) }?.forEach { cave ->
        findPaths(currentPath + cave, paths, check)
    }
}

fun isCaveBig(cave: String) = cave.first().isUpperCase()
