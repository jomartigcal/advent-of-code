//Day 1: Calorie Counting
//https://adventofcode.com/2022/day/1

import java.io.File

fun main() {
    val list = File("src/main/resources/Day01.txt").readLines()
    val caloriesPerElf = countCaloriesPerElf(list)

    val sortedCalories = caloriesPerElf.sortedByDescending { it.sum() }
    println(sortedCalories.first().sum())
    println(sortedCalories.take(3).sumOf { it.sum() })
}

private fun countCaloriesPerElf(calories: List<String>): List<List<Int>> {
    val caloriesPerElf = mutableListOf<List<Int>>()
    var tempList = mutableListOf<Int>()
    calories.forEach {
        if (it.isNotEmpty()) {
            tempList.add(it.toInt())
        } else {
            caloriesPerElf.add(tempList)
            tempList = mutableListOf()
        }
    }
    caloriesPerElf.add(tempList)

    return caloriesPerElf
}
