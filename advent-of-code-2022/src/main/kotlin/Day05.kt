//Day 05: Supply Stacks
//https://adventofcode.com/2022/day/5

import java.io.File
import kotlin.collections.ArrayDeque

data class Command(val itemsToMove: Int, val source: String, val destination: String) {
    companion object {
        fun fromString(command: String): Command {
            return Command(
                command.substringAfter("move ").substringBefore(" from").toInt(),
                command.substringAfter("from ").substringBefore(" to"),
                command.substringAfter("to ")
            )
        }
    }
}

fun main() {
    val input = File("src/main/resources/Day05.txt").readLines()

    val cratesAndLabel = input.takeWhile { it.isNotEmpty() }
    val labels = cratesAndLabel.last().split("   ").map { it.trim() }
    val crates = cratesAndLabel.dropLast(1).reversed()
    val commands = input.drop(cratesAndLabel.size + 1).map { Command.fromString(it) }

    rearrangeCrates9000(getStacks(labels, crates), commands)
    println()
    rearrangeCrates9001(getStacks(labels, crates), commands)
}

private fun getStacks(
    labels: List<String>,
    inputCrate: List<String>
) = labels.mapIndexed { i, label ->
    val stack = ArrayDeque<String>()
    inputCrate.forEach {
        it.getOrNull((i * 4) + 1)?.let { char ->
            if (!char.isWhitespace()) stack.addFirst(char.toString())
        }
    }
    label to stack
}.toMap()

fun rearrangeCrates9000(stacks: Map<String, ArrayDeque<String>>, commands: List<Command>) {
    commands.forEach { command ->
        (1..command.itemsToMove).forEach { _ ->
            val item = stacks[command.source]?.removeFirst()
            item?.let {
                stacks[command.destination]?.addFirst(item)
            }
        }
    }

    stacks.values.forEach {
        print(it.first())
    }
}

fun rearrangeCrates9001(stacks: Map<String, ArrayDeque<String>>, commands: List<Command>) {
    commands.forEach { command ->
        val items = mutableListOf<String>()
        (1..command.itemsToMove).forEach { _ ->
            stacks[command.source]?.removeFirst()?.let { items.add(it) }
        }
        items.reversed().forEach {
            stacks[command.destination]?.addFirst(it)
        }
    }

    stacks.values.forEach {
        print(it.first())
    }
}
