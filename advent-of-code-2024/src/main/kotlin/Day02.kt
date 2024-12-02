// Day 02: Red-Nosed Reports
// https://adventofcode.com/2024/day/2

import java.io.File

fun main() {
    val reports = File("src/main/resources/Day02.txt").readLines().map { report ->
        report.split(" ").map { it.toInt() }
    }

    reports.count { report ->
        isReportSafe(report)
    }.also { println(it) }

    reports.count { report ->
        isReportSafe(report) || isDampenedReportSafe(report)
    }.also { println(it) }
}


private fun isReportSafe(report: List<Int>): Boolean {
    val levelDeltas = report.zipWithNext { x, y -> y - x }
    return levelDeltas.all { it in 1..3 } || levelDeltas.all { it in -3..-1 }
}

private fun isDampenedReportSafe(report: List<Int>): Boolean {
    for (i in report.indices) {
        val dampenedReport = report.toMutableList().apply { removeAt(i) }
        if (isReportSafe(dampenedReport)) {
            return true
        }
    }

    return false
}
