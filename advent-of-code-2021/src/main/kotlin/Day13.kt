//Day 13: Transparent Origami
//https://adventofcode.com/2021/day/13

import java.io.File

fun main() {
    val dots = mutableListOf<Pair<Int, Int>>()
    val folds = mutableListOf<Fold>()

    val lines = File("src/main/resources/Day13.txt").readLines()
    lines.forEach {
        if (it.startsWith("fold")) {
            folds.add(Fold(it.substringAfter(" along ").first(), it.substringAfter("=").toInt()))
        } else if (it.isNotBlank()) {
            val (x, y) = it.split(",")
            dots.add(x.toInt() to y.toInt())
        }
    }

    val manual = Array(dots.maxOf { it.first } + 1) { Array(dots.maxOf { it.second } + 1) { 0 } }
    dots.forEach {
        manual[it.first][it.second]++
    }

    val manualFoldedOnce = foldManual(manual, folds.take(1))
    var dotsFromFold1 = 0
    for (i in 0 until manualFoldedOnce[0].size) {
        for (element in manualFoldedOnce) {
            if (element[i] >= 1) dotsFromFold1++
        }
    }
    println(dotsFromFold1)

    val foldedManual = foldManual(manual, folds)
    for (i in foldedManual[0].size - 1 downTo 0) {
        print("\n")
        for (j in foldedManual.size - 1 downTo 0) {
            if (foldedManual[j][i] >= 1) print("#") else print(" ")
        }
    }
}

fun foldManual(manual: Array<Array<Int>>, folds: List<Fold>): Array<Array<Int>> {
    var foldedManual = manual
    for (fold in folds) {
        foldedManual = if (fold.isAlongX) {
            foldHorizontally(foldedManual, fold.value)
        } else {
            foldVertically(foldedManual, fold.value)
        }
    }

    return foldedManual
}

fun foldHorizontally(original: Array<Array<Int>>, foldIndex: Int): Array<Array<Int>> {
    val y = original[0].size
    val array = Array(foldIndex) { Array(y) { 0 } }

    for (i in foldIndex + 1 until original.size) {
        for (j in 0 until y) {
            array[i - foldIndex - 1][j] = original[i][j] + original[(2 * foldIndex) - i][j]
        }
    }

    return array
}

fun foldVertically(original: Array<Array<Int>>, foldIndex: Int): Array<Array<Int>> {
    val y = original[0].size
    val array = Array(original.size) { Array(foldIndex) { 0 } }

    for (i in original.indices) {
        for (j in foldIndex + 1 until y) {
            array[i][j - foldIndex - 1] = original[i][j] + original[i][(2 * foldIndex) - j]
        }
    }

    return array
}

data class Fold(val axis: Char, val value: Int) {
    val isAlongX = axis == 'x'
}
