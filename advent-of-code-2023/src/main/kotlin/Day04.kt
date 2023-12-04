// Day 04: Scratchcards
// https://adventofcode.com/2023/day/4

import java.io.File
import kotlin.math.pow

fun main() {
    val cards = File("src/main/resources/Day04.txt").readLines()

    val cardToWinMap = cards.associate { card ->
        val number = card.substringAfter("Card ").substringBefore(":").trim().toInt()
        val (win, yours) = card.substringAfter(":").split(" |")

        val winningNumbers = win.split(" ").mapNotNull { it.toIntOrNull() }
        val yourNumbers = yours.split(" ").mapNotNull { it.toIntOrNull() }
        number to (winningNumbers intersect yourNumbers).size
    }

    println(cardToWinMap.values.sumOf { wins ->
        (2.0.pow(wins - 1)).toInt()
    })

    val cardToCountMap = cardToWinMap.keys.associateWith { 1 }.toMutableMap()
    for (cardNum in cardToWinMap.keys) {
        val wins = cardToWinMap[cardNum] ?: 0
        if (wins > 0) {
            val count = cardToCountMap[cardNum] ?: 0
            (cardNum + 1..cardNum + wins).forEach { copy ->
                if (cardToCountMap.containsKey(copy)) {
                    cardToCountMap[copy] = cardToCountMap[copy]?.plus(count) ?: count
                }
            }
        }
    }

    println(cardToCountMap.values.sum())
}
