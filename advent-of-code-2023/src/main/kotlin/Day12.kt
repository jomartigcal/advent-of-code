// Day 12: Hot Springs
// https://adventofcode.com/2023/day/12

import java.io.File

private const val WORKING = "."
private const val BROKEN = "#"
private const val UNKNOWN = "?"

fun main() {
    val rows = File("src/main/resources/Day12.txt").readLines()

    val sumOfPossileArrangements = rows.sumOf { row ->
        val (records, arrangement) = row.split(" ")
        getPossibleArrangemnts(records, arrangement.split(",").map { it.toInt() })
    }
    println(sumOfPossileArrangements)
}

private fun getPossibleArrangemnts(records: String, arrangement: List<Int>): Int {
    var count = 0
    val index = records.indexOf(UNKNOWN)

    count += count(records.replaceRange(index..index, WORKING), arrangement)
    count += count(records.replaceRange(index..index, BROKEN), arrangement)

    return count
}

private fun count(records: String, arrangement: List<Int>): Int {
    var count = 0

    if (records.contains(UNKNOWN).not() && arrangement ==
        records.split(WORKING)
            .filter { record -> record.contains(BROKEN) }
            .map { record -> record.count { it == BROKEN.first() } }
    ) {
        count++
    }

    if (records.contains(UNKNOWN)) {
        val index = records.indexOf(UNKNOWN)
        count += count(records.replaceRange(index..index, WORKING), arrangement)
        count += count(records.replaceRange(index..index, BROKEN), arrangement)
    }

    return count
}
