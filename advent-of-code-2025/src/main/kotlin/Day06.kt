import java.io.File

// Day 06: Trash Compactor
// https://adventofcode.com/2025/day/6

data class Problem(
    val operands: List<Long>,
    val operation: String
) {
    fun solve(): Long {
        return if (operation == "*") {
            operands.fold(1) { acc, next -> acc * next }
        } else if (operation == "+") {
            operands.sumOf { it }
        } else {
            0
        }
    }
}

fun main() {
    val lines = File("src/main/resources/Day06.txt").readLines()

    val numbers = lines.take(lines.size - 1).map { line ->
        line.split(" ").mapNotNull {
            if (it == "") null else it.toLong()
        }
    }
    val operands = (0 until numbers.first().size).map { x ->
        buildList {
            (0 until numbers.size).forEach { y ->
                add(numbers.get(y).get(x))
            }
        }
    }

    val operations = lines.last().split(" ").mapNotNull {
        if (it == "") null else it
    }
    operations.mapIndexed { index, operation ->
        Problem(
            operands = operands.get(index),
            operation = operation
        )
    }.sumOf {
        it.solve()
    }.also {
        println(it)
    }
    //TODO Part 2
}
