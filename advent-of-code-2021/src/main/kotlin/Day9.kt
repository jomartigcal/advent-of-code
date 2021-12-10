//Day 9: Smoke Basin
//https://adventofcode.com/2021/day/9

import java.io.File

fun main() {
    val heightMaps = File("src/main/resources/Day9.txt").readLines()
    val totalHeight = heightMaps.size
    val totalWidth = heightMaps.first().length

    val array = Array(totalHeight) { Array(totalWidth) { 0 } }
    heightMaps.forEachIndexed { index, heightmap ->
        heightmap.toList().forEachIndexed { heightIndex, height ->
            array[index][heightIndex] = height.digitToInt()
        }
    }

    val lowPoints = getLowPoints(array)
    val riskLevel = lowPoints.sumOf { index ->
        array[index.first][index.second] + 1
    }
    println(riskLevel)

    getLargestBasins(array, lowPoints)
}

fun getLowPoints(array: Array<Array<Int>>): List<Pair<Int, Int>> {
    val lowPoints = mutableListOf<Pair<Int, Int>>()
    for (x in array.indices) {
        for (y in 0 until array[x].size) {
            val cell = array[x][y]
            var isLower = true
            if (x > 0) isLower = isLower && cell < array[x - 1][y]
            if (x < array.size - 1) isLower = isLower && cell < array[x + 1][y]
            if (y > 0) isLower = isLower && cell < array[x][y - 1]
            if (y < array[x].size - 1) isLower = isLower && cell < array[x][y + 1]
            if (isLower) lowPoints.add(x to y)
        }
    }
    return lowPoints
}

fun getLargestBasins(array: Array<Array<Int>>, lowPoints: List<Pair<Int, Int>>) {
    val basinSizes = lowPoints.map { point ->
        var basin = mutableSetOf<Pair<Int, Int>>()
        var newBasin = mutableSetOf(point.first to point.second)
        while (newBasin != basin) {
            basin = newBasin
            newBasin = flowDownBasin(array, basin)
        }
        newBasin.size
    }.sortedDescending()

    println(basinSizes[0] * basinSizes[1] * basinSizes[2])
}

fun flowDownBasin(array: Array<Array<Int>>, oldBasin: Set<Pair<Int, Int>>): MutableSet<Pair<Int, Int>> {
    val newBasin = oldBasin.toMutableSet()

    oldBasin.forEach { point ->
        val x = point.first
        val y = point.second

        if (x > 0 && array[x - 1][y] != 9) newBasin.add(x - 1 to y)
        if (x < array.size - 1 && array[x + 1][y] != 9) newBasin.add(x + 1 to y)
        if (y > 0 && array[x][y - 1] != 9) newBasin.add(x to y - 1)
        if (y < array[x].size - 1 && array[x][y + 1] != 9) newBasin.add(x to y + 1)
    }

    return newBasin
}
