// Day 07: Camel Cards
// https://adventofcode.com/2023/day/7

import java.io.File

data class Card(val char: Char)

fun Card.getValue(hasJoker: Boolean): Int {
    return when {
        char.isDigit() -> char.digitToInt()
        char == 'T' -> 10
        char == 'J' && hasJoker.not() -> 11
        char == 'J' && hasJoker -> 0
        char == 'Q' -> 12
        char == 'K' -> 13
        char == 'A' -> 14
        else -> 0
    }
}

private val JOKER = Card('J')

data class Hand(val cards: List<Card>, val bid: Int) {
    fun getRank(hasJoker: Boolean): Int {
        var cardsForRanking = cards.toMutableList()
        if (hasJoker && cards.contains(JOKER)) {
            while (cardsForRanking.contains(JOKER)) {
                cardsForRanking.remove(JOKER)
            }
            if (cards.distinct().size != 5) {
                val idealCard =
                    if (cards.count { it == JOKER } >= 3
                        && cards.distinct().size == 2
                    ) {
                        cards.find { it != JOKER }
                    } else if (cards.count { it == JOKER } == 5) {
                        Card('A')
                    } else {
                        cardsForRanking.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
                    }
                if (idealCard != null) {
                    cardsForRanking = cards.map {
                        if (it == JOKER) idealCard else it
                    }.toMutableList()
                }
            } else {
                val idealCard = cards.minByOrNull { it.getValue(false) }
                if (idealCard != null) {
                    cardsForRanking = cards.map {
                        if (it == JOKER) idealCard else it
                    }.toMutableList()
                }
            }
        }

        val x = if (cardsForRanking.distinct().size == 1) {
            6 // Five of a kinad
        } else if (
            cardsForRanking.distinct().size == 2 &&
            cardsForRanking.groupingBy { it }.eachCount().values.max() == 4
        ) {
            5 // Four of a kind
        } else if (cardsForRanking.distinct().size == 2) {
            4 // Full House
        } else if (cardsForRanking.distinct().size == 3
            && cardsForRanking.groupingBy { it }.eachCount().values.max() == 3
        ) {
            3 // Three of a kind
        } else if (cardsForRanking.distinct().size == 3) {
            2 // Two pair
        } else if (cardsForRanking.distinct().size == 4) {
            1 // One pair
        } else {
            0 // High card
        }
        return x
    }
}

fun main() {
    val lines = File("src/main/resources/Day07.txt").readLines()
    val hands = lines.map { hand ->
        val (cards, bid) = hand.split(" ")
        Hand(
            cards.toList().map { card ->
                Card(card)
            },
            bid.toInt()
        )
    }

    println(getTotalWinnings(hands.toList(), hasJoker = false))
    println(getTotalWinnings(hands.toList(), hasJoker = true))
}

private fun getTotalWinnings(hands: List<Hand>, hasJoker: Boolean) = hands.sortedWith(
    compareBy<Hand> { it.getRank(hasJoker) }
        .thenBy { it.cards[0].getValue(hasJoker) }
        .thenBy { it.cards[1].getValue(hasJoker) }
        .thenBy { it.cards[2].getValue(hasJoker) }
        .thenBy { it.cards[3].getValue(hasJoker) }
        .thenBy { it.cards[4].getValue(hasJoker) }
).mapIndexed { index, hand ->
    hand.bid * (index + 1)
}.sum()