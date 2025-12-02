import java.io.File
import kotlin.collections.chunked

// Day 02: Gift Shop
// https://adventofcode.com/2025/day/2

fun main() {
    val idRanges = File("src/main/resources/Day02.txt").readLines().single()
        .split(",")
        .map {
            val (id1, id2) = it.split("-")
            id1.toLong() to id2.toLong()
        }

    countInvalidIdsFrom(idRanges) { id: String ->
        id.take(id.length / 2) == id.drop(id.length / 2)
    }

    countInvalidIdsFrom(idRanges) { id: String ->
        var invalid = false
        val digits = id.toList()

        for (x in 1..digits.size / 2) {
            if (digits.chunked(x).distinct().size == 1) {
                invalid = true
                break
            }
        }
        invalid
    }
}

private fun countInvalidIdsFrom(idRanges: List<Pair<Long, Long>>, validateId: (String) -> Boolean) {
    idRanges.map { idRange ->
        (idRange.first..idRange.second).toList()
            .map { it.toString() }
            .filter { id ->
                validateId(id)
            }
    }.flatten().sumOf {
        it.toLong()
    }.also { println(it) }
}
