import java.io.File

// Day 06: Trash Compactor
// https://adventofcode.com/2025/day/6

data class Problem(
    val operands: List<String>,
    val operation: String
) {
    fun solve(): Long {
        return when (operation) {
            "*" -> {
                operands.fold(1) { acc, next -> acc * next.trim().toLong() }
            }

            "+" -> {
                operands.sumOf { it.trim().toLong() }
            }

            else -> 0
        }
    }

    fun solveRTL(): Long {
        val rtlOperands = (0 until operands.maxOf { it.length }).mapIndexed { index, num ->
            operands.mapNotNull {
                if (it.getOrElse(index, { ' ' }) != ' ') it[index].digitToInt().toLong() else null
            }
        }
        return when (operation) {
            "*" -> {
                rtlOperands.fold(1L) { acc, next -> acc * next.joinToString("").toLong() }
            }

            "+" -> {
                rtlOperands.sumOf { it.joinToString("").toLong() }
            }

            else -> 0
        }
    }
}

fun main() {
    val lines = File("src/main/resources/Day06.txt").readLines()

    val length = lines.maxOf { it.length }
    val spacers = (0 until length).mapIndexedNotNull { index, _ ->
        var spacer = true
        for (x in 0 until lines.size) {
            if (index >= lines[x].length) {
                spacer = false
                break
            }
            spacer = spacer && lines[x][index] == ' '
            if (spacer.not()) break
        }
        if (spacer) index else null
    }.toMutableList().apply {
        add(0, 0)
        add(length)
    }.windowed(2)

    val problems = spacers.mapIndexed { index, it ->
        val start = if (it.first() == 0) it.first() else it.first() + 1
        val operation = if (index >= spacers.size - 1) {
            lines.last().substring(start)
        } else {
            lines.last().substring(start, it.last())
        }

        val inputs = lines.take(lines.size - 1).mapIndexed { index, line ->
            if (index > spacers.size) {
                line.substring(start)
            } else {
                val last = if (it.last() >= line.length) line.length else it.last()
                line.substring(start, last)
            }
        }

        Problem(operands = inputs, operation = operation.trim())
    }

    problems.sumOf {
        it.solve()
    }.also {
        println(it)
    }

    problems.sumOf {
        it.solveRTL()
    }.also {
        println(it)
    }

}
