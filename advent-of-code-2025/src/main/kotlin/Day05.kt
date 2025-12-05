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
    }.also { println(it)}

    //TODO Part 2
}
