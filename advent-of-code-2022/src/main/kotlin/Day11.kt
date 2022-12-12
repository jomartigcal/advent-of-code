//Day 11: Monkey in the Middle
//https://adventofcode.com/2022/day/11

import java.io.File
import java.math.BigInteger

data class Monkey(
    val id: Int,
    val items: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val test: (BigInteger) -> Boolean,
    val monkeyWhenTrue: () -> Int,
    val monkeyWhenFalse: () -> Int
) {
    var inspections: Int = 0

    companion object {
        fun fromNote(notes: List<String>): Monkey {
            return Monkey(
                notes[0].substringAfter("Monkey ").substringBefore(":").toInt(),
                notes[1].substringAfter("Starting items: ").split(", ").map { it.toBigInteger() }.toMutableList(),
                { old ->
                    val operator = notes[2].substringAfter(" new = old ").first()
                    val otherNumber = notes[2].substringAfter("$operator ")
                    val operand = if (otherNumber == "old") old else otherNumber.toBigInteger()
                    return@Monkey when (operator) {
                        '*' -> old.multiply(operand)
                        '+' -> old.add(operand)
                        else -> old
                    }
                },
                { worryLevel ->
                    val divisibleBy = notes[3].substringAfter("by ")
                    return@Monkey BigInteger.ZERO.equals(worryLevel.mod(divisibleBy.toBigInteger()))
                },
                {
                    return@Monkey notes[4].substringAfter("monkey ").toInt()
                },
                {
                    return@Monkey notes[5].substringAfter("monkey ").toInt()
                }
            )
        }
    }
}

fun main() {
    val input = File("src/main/resources/Day11.txt").readLines().chunked(7)
    findMonkeyBusiness(input.map { Monkey.fromNote(it) }, 20, true)
//    findMonkeyBusiness(input.map { Monkey.fromNote(it) }, 10_000, false)
}

private fun findMonkeyBusiness(monkeys: List<Monkey>, rounds: Int, shouldWorry: Boolean) {
    (1..rounds).forEach { _ ->
        monkeys.forEach { monkey: Monkey ->
            val worries = monkey.items.toMutableList().listIterator()
            while (worries.hasNext()) {
                val old = worries.next()
                val worry =
                    if (shouldWorry) monkey.operation(old).div(BigInteger.valueOf(3L)) else monkey.operation(old)
                if (monkey.test(worry)) {
                    monkeys[monkey.monkeyWhenTrue()].items.add(worry)
                } else {
                    monkeys[monkey.monkeyWhenFalse()].items.add(worry)
                }
                monkey.items.remove(old)
                monkey.inspections++
            }
        }
    }

    val topMonkeys = monkeys.sortedByDescending { it.inspections }.take(2)
    println(topMonkeys.first().inspections * topMonkeys.last().inspections)
}
