// Day 05: If You Give A Seed A Fertilizer
// https://adventofcode.com/2023/day/5

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day05.txt").readLines()

    val seeds = lines.first().substringAfter("seeds: ").split(" ").map {
        it.toLong()
    }

    var index = 3
    val seedToSoilList = lines.drop(index).takeWhile { it.isNotEmpty() }

    index += seedToSoilList.size + 2
    val soilToFertilizerList = lines.drop(index).takeWhile { it.isNotEmpty() }

    index += soilToFertilizerList.size + 2
    val fertilizerToWaterList = lines.drop(index).takeWhile { it.isNotEmpty() }

    index += fertilizerToWaterList.size + 2
    val waterToLightList = lines.drop(index).takeWhile { it.isNotEmpty() }

    index += waterToLightList.size + 2
    val lightToTempList = lines.drop(index).takeWhile { it.isNotEmpty() }

    index += lightToTempList.size + 2
    val tempToHumidityList = lines.drop(index).takeWhile { it.isNotEmpty() }

    index += tempToHumidityList.size + 2
    val humidityToLocList = lines.drop(index).takeWhile { it.isNotEmpty() }

    val minLoc = seeds.minOf { seed ->
        val soil = getDestinationFromList(seedToSoilList, seed)
        val fertilizer = getDestinationFromList(soilToFertilizerList, soil)
        val water = getDestinationFromList(fertilizerToWaterList, fertilizer)
        val light = getDestinationFromList(waterToLightList, water)
        val temp = getDestinationFromList(lightToTempList, light)
        val humidity = getDestinationFromList(tempToHumidityList, temp)
        getDestinationFromList(humidityToLocList, humidity)
    }
    println(minLoc)
}

private fun getDestinationFromList(list: List<String>, input: Long): Long {
    val sourceToDestination = list.find { range ->
        val (_, source, length) = range.split(" ").map { it.toLong() }

        input in source..source + length
    }

    return if (sourceToDestination != null) {
        val (destination, source, length) = sourceToDestination.split(" ").map { it.toLong() }
        destination + (input - source)
    } else {
        input
    }
}
