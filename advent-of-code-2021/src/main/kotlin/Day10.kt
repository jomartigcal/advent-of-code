//Day 10: Syntax Scoring
//https://adventofcode.com/2021/day/10

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day10.txt").readLines()
    countSyntaxErrorScore(lines)
    countIncompleteScore(lines)
}

fun countSyntaxErrorScore(lines: List<String>) {
    val syntaxScore = lines.sumOf { line ->
        when (getInvalidChunk(line.toList())) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }.toInt()
    }
    println(syntaxScore)
}

fun getInvalidChunk(chunks: List<Char>): Char? {
    val stack = mutableListOf<Char>()
    chunks.forEach { chunk ->
        when (chunk) {
            in listOf('{', '(', '[', '<') -> stack.add(chunk)
            ')' -> if (stack.lastOrNull() == '(') stack.removeLast() else return chunk
            ']' -> if (stack.lastOrNull() == '[') stack.removeLast() else return chunk
            '}' -> if (stack.lastOrNull() == '{') stack.removeLast() else return chunk
            '>' -> if (stack.lastOrNull() == '<') stack.removeLast() else return chunk
        }
    }
    return null
}

fun countIncompleteScore(lines: List<String>) {
    val scores = lines.filter { getInvalidChunk(it.toList()) == null }.map {
        getChunksToComplete(it).fold(0L) { acc, chunk ->
            (acc * 5) + when (chunk) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> 0
            }
        }
    }.sorted()
    println(scores[scores.size / 2])
}

fun getChunksToComplete(incompleteChunks: String): List<Char> {
    val chunks = mutableListOf<Char>()
    incompleteChunks.toList().forEach { chunk ->
        when (chunk) {
            in listOf('{', '(', '[', '<') -> chunks.add(chunk)
            in listOf('}', ')', ']', '>') -> chunks.removeLastOrNull()
        }
    }
    chunks.replaceAll { chunk ->
        when (chunk) {
            '(' -> ')'
            '[' -> ']'
            '{' -> '}'
            '<' -> '>'
            else -> chunk
        }
    }

    return chunks.reversed()
}
