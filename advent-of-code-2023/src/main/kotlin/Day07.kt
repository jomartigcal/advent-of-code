// Day 07: Camel Cards
// https://adventofcode.com/2023/day/7

import java.io.File

enum class Card(val value: Char) {
    A('A'), K('K'), Q('Q'), J('J'), T('T'),
    C9('9'), C8('8'), C7('7'), C6('6'),
    C5('5'), C4('4'), C3('3'), C2('2')
}

data class Hand(val cards: List<Card>, val bid: Int) {
    fun getRank(): Int {
        return if (cards.distinct().size == 1) {
            6 // Five of a kinad
        } else if (
            cards.groupingBy { it }.eachCount().values.max() == 4) {
            5 // Four of a kind
        } else if (cards.distinct().size == 2) {
            4 // Full House
        } else if (cards.distinct().size == 3
            && cards.groupingBy { it }.eachCount().values.max() == 3
        ) {
            3 // Three of a kind
        } else if (cards.distinct().size == 3) {
            2 // Two pair
        } else if (cards.distinct().size == 4) {
            1 // One pair
        } else {
            0 // High card
        }
    }
}

fun main() {
    val lines = File("src/main/resources/Day00.txt").readLines()
    val hands = lines.map { hand ->
        val (cards, bid) = hand.split(" ")
        Hand(
            cards.toList().map { card ->
                Card.entries.find {
                    it.value == card
                }!!
            },
            bid.toInt()
        )
    }

    val totalWinning = hands.sortedWith(
        compareBy<Hand> { it.getRank() }
            .thenByDescending { it.cards[0].ordinal }
            .thenByDescending { it.cards[1].ordinal }
            .thenByDescending { it.cards[2].ordinal }
            .thenByDescending { it.cards[3].ordinal }
            .thenByDescending { it.cards[4].ordinal }
    ).mapIndexed { index, hand ->
        hand.bid * (index + 1)
    }.sum()

    println(totalWinning)
}