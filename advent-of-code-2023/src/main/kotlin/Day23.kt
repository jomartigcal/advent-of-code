// Day 23: A Long Walk
// https://adventofcode.com/2023/day/23

import java.io.File

private const val FOREST = '#'
private const val PATH = '.'

data class Tile(val char: Char, val xy: Pair<Int, Int>)

fun main() {
    val trails = File("src/main/resources/Day23.txt").readLines().mapIndexed { x, chars ->
        chars.toList().mapIndexed { y, char ->
            if (char != FOREST) Tile(char, x to y) else null
        }
    }.flatten().filterNotNull()

    val startTile = trails.find { it.char == PATH} ?: trails.first()
    val endTile = trails.findLast { it.char == PATH } ?: trails.last()

    fun getNeighborTiles(tile: Tile, steps: List<Tile>): List<Tile> {
        val (x, y) = tile.xy
        val neighbour: Tile?
        when (tile.char) {
            '^' -> {
                neighbour = trails.find { it.xy == x - 1 to y }
                return if (neighbour != null && neighbour !in steps) {
                    listOf(neighbour)
                } else {
                    emptyList()
                }
            }
            'v' -> {
                neighbour = trails.find { it.xy == x + 1 to y }
                return if (neighbour != null && neighbour !in steps) {
                    listOf(neighbour)
                } else {
                    emptyList()
                }
            }
            '<' -> {
                neighbour = trails.find { it.xy == x to y - 1 }
                return if (neighbour != null && neighbour !in steps) {
                    listOf(neighbour)
                } else {
                    emptyList()
                }
            }
            '>' -> {
                neighbour = trails.find { it.xy == x to y + 1 }
                return if (neighbour != null && neighbour !in steps) {
                    listOf(neighbour)
                } else {
                    emptyList()
                }
            }
            else -> return buildList {
                var neighbor = trails.find { it.xy == x - 1 to y }
                if (neighbor != null && neighbor !in steps) add(neighbor)

                neighbor = trails.find { it.xy == x + 1 to y }
                if (neighbor != null && neighbor !in steps) add(neighbor)

                neighbor = trails.find { it.xy == x to y - 1 }
                if (neighbor != null && neighbor !in steps) add(neighbor)

                neighbor = trails.find { it.xy == x to y + 1 }
                if (neighbor != null && neighbor !in steps) add(neighbor)
            }
        }
    }

    fun takeAHike(steps: List<Tile>): List<List<Tile>> {
        val lastStep = steps.last()
        return if (lastStep == endTile) {
            listOf(steps)
        } else {
            getNeighborTiles(lastStep, steps).flatMap {
                takeAHike(steps + it)
            }
        }
    }

    val steps = takeAHike(getNeighborTiles(startTile, emptyList()))
    println(steps.maxOf { it.size })

    //TODO Part 2
}
