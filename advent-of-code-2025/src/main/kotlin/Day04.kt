import java.io.File

// Day 04: Printing Department
// https://adventofcode.com/2025/day/4

private const val PAPER = '@'

fun main() {
    val grid = File("src/main/resources/Day04.txt").readLines().map {
        it.toList()
    }

    val accessiblePaperRolls = forkliftPapers(grid)
    println(accessiblePaperRolls)

    var mutableGrid = grid.toMutableList()
    var removedPapers = 0
    while (true) {
        val papersToRemove = forkliftPapers(mutableGrid)
        if (papersToRemove == 0) break
        removedPapers += papersToRemove

        mutableGrid = mutableGrid.mapIndexed { x, list ->
            list.mapIndexed { y, item ->
                if (getNeighbours(mutableGrid, item, x, y) in 0..3) '.' else item
            }
        }.toMutableList()
    }
    println(removedPapers)
}

private fun getNeighbours(grid: List<List<Char>>, item: Char, x: Int, y: Int): Int? {
    val height = grid.size
    val width = grid.first().size

    if (item != PAPER) return null

    var neighbours = 0
    if (x - 1 >= 0) {
        if (y - 1 >= 0 && grid.get(x - 1).get(y - 1) == PAPER) neighbours++
        if (grid.get(x - 1).get(y) == PAPER) neighbours++
        if (y + 1 < height && grid.get(x - 1).get(y + 1) == PAPER) neighbours++
    }

    if (y - 1 >= 0 && grid.get(x).get(y - 1) == PAPER) neighbours++
    if (y + 1 < height && grid.get(x).get(y + 1) == PAPER) neighbours++

    if (x + 1 < width) {
        if (y - 1 >= 0 && grid.get(x + 1).get(y - 1) == PAPER) neighbours++
        if (grid.get(x + 1).get(y) == PAPER) neighbours++
        if (y + 1 < height && grid.get(x + 1).get(y + 1) == PAPER) neighbours++
    }

    return neighbours
}

private fun forkliftPapers(grid: List<List<Char>>): Int {
    return grid.mapIndexed { x, list ->
        list.mapIndexed { y, item ->
            getNeighbours(grid, item, x, y)
        }
    }.flatten().count { it in 0..3 }
}
