//Day 02: Rock Paper Scissors
//https://adventofcode.com/2022/day/2

import java.io.File

enum class Hand(val opponentCode: Char, val score: Int) {
    ROCK('A', 1),
    PAPER('B', 2),
    SCISSOR('C', 3);

    fun versus(opponent: Hand): Boolean {
        return when (this) {
            ROCK -> opponent == SCISSOR
            PAPER -> opponent == ROCK
            SCISSOR -> opponent == PAPER
        }
    }

    companion object {
        fun fromChar(char: Char): Hand? {
            return values().find {
                it.opponentCode == char
            }
        }
    }
}

fun main() {
    val list = File("src/main/resources/Day02.txt").readLines()
    opponentThenYours(list)
    opponentThenResult(list)
}

private fun rockPaperScissor(opponent: Hand?, yours: Hand?): Int {
    if (opponent == yours) return 3

    return if (yours!!.versus(opponent!!)) 6 else 0
}

private fun opponentThenYours(list: List<String>) {
    val xyzMap = mapOf('X' to Hand.ROCK, 'Y' to Hand.PAPER, 'Z' to Hand.SCISSOR)
    val totalScore = list.sumOf { input ->
        val hands = input.split(" ").map { it.toCharArray().first() }
        val opponents = Hand.fromChar(hands.first())
        val yours = xyzMap[hands.last()]
        yours!!.score + rockPaperScissor(opponents, yours)
    }
    println(totalScore)
}

private fun opponentThenResult(list: List<String>) {
    val totalScore = list.sumOf { input ->
        val hands = input.split(" ").map { it.toCharArray().first() }
        val opponents = Hand.fromChar(hands.first())
        val yours = getHandToShow(opponents!!, hands.last())
        yours.score + rockPaperScissor(opponents, yours)
    }
    println(totalScore)
}

private fun getHandToShow(opponent: Hand, result: Char): Hand {
    if (result == 'Y') return opponent

    return if (result == 'X') {
        Hand.values().find {
            it != opponent && !it.versus(opponent)
        }!!
    } else { // if (result == 'Z') {
        Hand.values().find {
            it.versus(opponent)
        }!!
    }
}
