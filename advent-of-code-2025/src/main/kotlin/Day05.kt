import java.io.File

// Day 05: Cafeteria
// https://adventofcode.com/2025/day/5

fun main() {
    val database = File("src/main/resources/Day05.txt").readLines()
    val ingredientRanges = database.takeWhile {
        it.isNotEmpty()
    }.map {
        val (x, y) = it.split("-")
        x.toLong()..y.toLong()
    }.sortedBy { it.first }
    val ingredientIds = database.drop(ingredientRanges.size + 1).map { it.toLong() }

    ingredientIds.count {
        var fresh = false
        for (range in ingredientRanges) {
            if (it in range) {
                fresh = true
                break
            }
        }
        fresh
    }.also { println(it) }

    var freshIngredients = ingredientRanges.first().last - ingredientRanges.first().first + 1
    var previousRange = ingredientRanges.first()

    ingredientRanges.drop(1).forEach { newRange ->
        if (newRange.last > previousRange.last && newRange.first > previousRange.first
            && newRange.first > previousRange.last
            ) {
            freshIngredients += newRange.last - newRange.first + 1
            previousRange = newRange
        } else if (newRange.last > previousRange.last && newRange.first <= previousRange.last) {
            freshIngredients += newRange.last - previousRange.last
            previousRange = newRange
        }
    }
    println(freshIngredients)
}
