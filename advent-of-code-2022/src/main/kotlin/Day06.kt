//Day 06: Tuning Trouble
//https://adventofcode.com/2022/day/6

import java.io.File

fun main() {
    val dataStream = File("src/main/resources/Day06.txt").readLines().first()
    findMessageStartMarker(dataStream, 4)
    findMessageStartMarker(dataStream, 14)
}

private fun findMessageStartMarker(dataStream: String, messageSize: Int) {
    println(dataStream.toList().windowed(messageSize, 1).indexOfFirst {
        it.distinct().size == messageSize
    } + messageSize)
}
