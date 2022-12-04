//Day 04: Camp Cleanup
//https://adventofcode.com/2022/day/4

import java.io.File

private const val HYPEN = "-"
private const val SEPARATOR = ","

fun main() {
    val sectionAssignments = File("src/main/resources/Day04.txt").readLines()
    val assignments = sectionAssignments.map { sectionAssignment ->
        sectionAssignment.split(SEPARATOR).map { assignment ->
            (assignment.substringBefore(HYPEN).toInt()..assignment.substringAfter(HYPEN).toInt()).toSet()
        }

    }

    checkFullOverlaps(assignments)
    checkAnyOverlap(assignments)
}

private fun checkFullOverlaps(assignments: List<List<Set<Int>>>) {
    val conflicts = assignments.count { assignment ->
        val assignment1 = assignment.first()
        val assignment2 = assignment.last()
        assignment1.containsAll(assignment2) || assignment2.containsAll(assignment1)
    }
    println(conflicts)
}

private fun checkAnyOverlap(assignments: List<List<Set<Int>>>) {
    val conflicts = assignments.count { assignment ->
        val assignment1 = assignment.first()
        val assignment2 = assignment.last()

        (assignment1 intersect assignment2).isNotEmpty()
    }
    println(conflicts)
}