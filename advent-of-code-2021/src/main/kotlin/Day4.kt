//Day 4: Giant Squid
//https://adventofcode.com/2021/day/4

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day4.txt").readLines()
    val draws = lines.first().split(",").map { it.toInt() }

    val boards = lines.asSequence().drop(1).chunked(6).map { board ->
        board.drop(1).map { it.split(" ") }
    }.map {
        it.flatten().filter { number -> number.isNotBlank() }.map { number ->
            BingoCell(number.toInt(), false)
        }
    }.map { BingoBoard(it.toMutableList()) }.toList()

    println(bingoFirstWin(draws, boards.toList()))
    println(bingoLastWin(draws, boards.toList()))
}

fun bingoFirstWin(draws: List<Int>, boards: List<BingoBoard>): Int {
    draws.forEach { draw ->
        boards.forEach { board ->
            board.mark(draw)
            if (board.isAWinner()) {
                return draw * board.unMarkedCellsTotal()
            }
        }
    }
    return 0
}

fun bingoLastWin(draws: List<Int>, boards: List<BingoBoard>): Int {
    var remainingBoards = boards.toMutableList()

    draws.forEach { draw ->
        remainingBoards.forEach { board ->
            board.mark(draw)
            if (board.isAWinner()) {
                if (remainingBoards.size == 1) {
                    return draw * remainingBoards.first().unMarkedCellsTotal()
                }
                remainingBoards = (remainingBoards - board) as MutableList<BingoBoard>
            }
        }
    }
    return 0
}

data class BingoCell(var value: Int, var marked: Boolean = false)

data class BingoBoard(val cells: MutableList<BingoCell>) {
    fun mark(number: Int) {
        cells.replaceAll { if (it.value == number) it.copy(marked = true) else it }
    }

    fun unMarkedCellsTotal(): Int = cells.filter { it.marked.not() }.sumOf { it.value }

    fun isAWinner(): Boolean = rowHasBingo() || columnHasBingo()

    private fun columnHasBingo(): Boolean {
        for (i in 0 until 5) {
            if (cells.filterIndexed { index, _ -> index % 5 == i }.all { it.marked }) return true

        }
        return false
    }

    private fun rowHasBingo(): Boolean {
        return cells.chunked(5).any { row -> row.all { it.marked } }
    }
}
