// Day 22: Monkey Market
// https://adventofcode.com/2024/day/22

import java.io.File

fun main() {
    val numbers = File("src/main/resources/Day22.txt").readLines().map { it.toLong() }

    val loops = 2_000
    numbers.sumOf { number ->
        getFinalSecretNumber(initialValue = number, loops)
    }.also { println(it) }
}

private fun getFinalSecretNumber(initialValue: Long, loops: Int): Long {
    var number = initialValue
    repeat(loops) {
        number = mix(number, number * 64)
        number = prune(number)
        number = mix(number, number / 32)
        number = prune(number)
        number = mix(number, number * 2048)
        number = prune(number)
    }
    return number
}

private fun mix(number: Long, mixee: Long): Long {
    return number.xor(mixee)
}

private fun prune(number: Long): Long {
    return number % 16777216
}