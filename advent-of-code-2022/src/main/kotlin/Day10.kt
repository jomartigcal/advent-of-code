//Day 10: Cathode-Ray Tube
//https://adventofcode.com/2022/day/10

import java.io.File

data class Instruction(val registerInc: Int) {
    val noop = registerInc == 0

    companion object {
        private val ADD = "addx "

        fun fromString(string: String): Instruction {
            return if (string.startsWith(ADD)) {
                Instruction(string.substringAfter(ADD).toInt())
            } else { //noop
                Instruction(0)
            }
        }
    }
}

fun main() {
    val lines = File("src/main/resources/Day10.txt").readLines()
    val instructions = lines.map { Instruction.fromString(it) }

    val registers = instructions.runningFold(1) { acc, instruction ->
        acc + instruction.registerInc
    }

    val cycles = instructions.map {
        if (it.noop) 1 else 2
    }.runningFold(1) { acc, x -> acc + x }
    findSignalStrengths(registers, cycles)
    renderCRTSprites(instructions,registers, cycles)
}

private fun findSignalStrengths(registers: List<Int>, cycles: List<Int>) {
    val sixSignalStrengths = (20..220 step 40).sumOf { cycle ->
        val index = if (cycles.contains(cycle)) cycles.indexOf(cycle) else cycles.indexOf(cycle - 1)
        cycle * registers[index]
    }
    println(sixSignalStrengths)
}

fun renderCRTSprites(instructions: List<Instruction>, registers: List<Int>, cycles: List<Int>) {
    val LIT = "#"
    val DARK = "."

    var cycle = 1
    val crt = mutableListOf<String>()
    instructions.forEach {
        val index = if (cycles.contains(cycle)) cycles.indexOf(cycle) else cycles.indexOf(cycle - 1)

        val sprite = mutableListOf(registers[index], registers[index] + 1, registers[index] + 2)

        if (it.noop) {
            crt += if (inSprite(sprite, cycle)) LIT else DARK
            cycle++
        } else {
            crt += if (inSprite(sprite, cycle)) LIT else DARK
            cycle++
            crt += if (inSprite(sprite, cycle)) LIT else DARK
            cycle++
        }
    }

    crt.chunked(40).forEach {
        println(it.joinToString(""))
    }
}

private fun inSprite(sprite: List<Int>, cycle: Int): Boolean {
    if (cycle % 40 == 0) return 40 in sprite
    return (cycle % 40 in sprite)
}