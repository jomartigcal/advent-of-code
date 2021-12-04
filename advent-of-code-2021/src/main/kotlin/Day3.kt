//Day 3: Binary Diagnostic
//https://adventofcode.com/2021/day/3

import java.io.File
import kotlin.math.pow

fun main() {
    val list = File("src/main/resources/Day3.txt").readLines()
    getPowerConsumption(list)
    getLifeSupportRating(list)
}

fun getPowerConsumption(list: List<String>) {
    val bits = getBitArrayForList(list)

    val gammaRate = mutableListOf<Char>()
    val epsilonRate = mutableListOf<Char>()
    bits.forEach { binary ->
        val (most, least) = getMostAndLeastCommmon(binary)
        gammaRate.add(most)
        epsilonRate.add(least)
    }

    println(binaryToDecimal(gammaRate.joinToString("")) * binaryToDecimal(epsilonRate.joinToString("")))
}

fun getLifeSupportRating(list: List<String>) {
    println(getRating(list, "oxygen") * getRating(list, "c02"))
}

fun getRating(list: List<String>, rating: String): Int {
    val mutableList = list.toMutableList()
    for (i in 0 until list.first().length) {
        if (mutableList.size == 1) break

        val bits = getBitArrayForList(mutableList)
        val numbers = getBitCount(bits[i])
        if (numbers['0']!! > numbers['1']!!) {
            mutableList.removeIf { it[i] == if (rating == "oxygen") '1' else '0' }
        } else if (numbers['1']!! > numbers['0']!!) {
            mutableList.removeIf { it[i] == if (rating == "oxygen") '0' else '1' }
        } else {
            mutableList.removeIf { it[i] == if (rating == "oxygen") '0' else '1' }
        }
    }

    return binaryToDecimal(mutableList.first())
}

fun getBitArrayForList(list: List<String>): Array<String> {
    val bits = Array(list.first().length) { "" }

    list.forEach { binary ->
        binary.toList().forEachIndexed { index, char ->
            bits[index] = bits[index] + char
        }
    }
    return bits
}

fun getMostAndLeastCommmon(binary: String): Pair<Char, Char> {
    val numbers = getBitCount(binary)

    return if (numbers['0']!! > numbers['1']!!) {
        '0' to '1'
    } else {
        '1' to '0'
    }
}

fun getBitCount(string: String): Map<Char, Int> {
    return string.toList().groupingBy { it }.eachCount()
}

fun binaryToDecimal(binary: String): Int {
    return binary.toInt(2)
}
