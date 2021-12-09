//Day 8: Seven Segment Search
//https://adventofcode.com/2021/day/8

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day8.txt").readLines()
    count1478FromOutput(lines.map { it.substringAfter(" | ").split(" ") })
    countAll(lines)
}

fun count1478FromOutput(outputs: List<List<String>>) {
    val count = outputs.sumOf { output ->
        output.count { it.length in listOf(2, 3, 4, 7) }
    }
    println(count)
}

fun countAll(lines: List<String>) {
    val sum = lines.sumOf { line ->
        decode(
            line.substringBefore(" | ").split(" "),
            line.substringAfter(" | ").split(" ")
        )
    }
    println(sum)
}

fun decode(input: List<String>, output: List<String>): Int {
    val cf = input.find { it.length == 2 }!!
    val acf = input.find { it.length == 3 }!!
    val bcdf = input.find { it.length == 4 }!!
    val abcdefg = input.find { it.length == 7 }!!
    val abcdfg = input.find { it.length == 6 && (it.toSet() intersect bcdf.toSet()).size == 4 }!!
    val abdefg = input.find { it.length == 6 && (it.toSet() intersect acf.toSet()).size == 2 }!!
    val abcefg = input.find { it.length == 6 && it != abcdfg && it != abdefg }!!
    val acdfg = input.find { it.length == 5 && (it.toSet() intersect acf.toSet()).size == 3 }!!
    val acdeg = input.find { it.length == 5 && (it.toSet() intersect bcdf.toSet()).size == 2 }!!
    val abdfg = input.find { it.length == 5 && it != acdfg && it != acdeg }!!

    val map = mapOf(
        abcefg to 0, cf to 1, acdeg to 2, acdfg to 3, bcdf to 4, abdfg to 5,
        abdefg to 6, acf to 7, abcdefg to 8, abcdfg to 9,
    ).mapKeys { it.key.toSet().sorted().joinToString("") }

    val sum = output.map {
        map[it.toSet().sorted().joinToString("")]!!
    }.joinToString("")

    return sum.toInt()
}
