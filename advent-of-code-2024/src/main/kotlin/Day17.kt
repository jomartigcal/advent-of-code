// Day 17: Chronospatial Computer
// https://adventofcode.com/2024/day/17

import java.io.File
import kotlin.math.pow

fun main() {
    val lines = File("src/main/resources/Day17.txt").readLines()

    var registerA = lines.first().substringAfter("Register A: ").toLong()
    var registerB = lines[1].substringAfter("Register B: ").toLong()
    var registerC = lines[2].substringAfter("Register C: ").toLong()
    val program = lines.last().substringAfter("Program: ").split(",").map {
        it.toLong()
    }.chunked(2)

    fun getComboOperand(operand: Long): Long {
        return when (operand) {
            in 0..3 -> operand
            4L -> registerA
            5L -> registerB
            6L -> registerC
            else -> throw Exception("reserved and will not appear")
        }
    }

    val output = mutableListOf<Long>()
    var pointer = 0

    do {
        val (opCode, operand) = program[pointer]

        when (opCode.toInt()) {
            1 -> {
                registerB = registerB.xor(operand)
            }

            2 -> {
                registerB = getComboOperand(operand) % 8
            }

            3 -> {
                if (registerA != 0L) {
                    pointer = operand.toInt()
                }
            }

            4 -> {
                registerB = registerB.xor(registerC)
            }

            5 -> {
                output += getComboOperand(operand) % 8
            }

            0, 6, 7 -> {
                val dv = (registerA / (2.0.pow(getComboOperand(operand).toDouble()))).toLong()
                when (opCode) {
                    0L -> {
                        registerA = dv
                    }

                    6L -> {
                        registerB = dv
                    }

                    else -> {
                        registerC = dv
                    }
                }
            }
        }
        if (opCode != 3L || registerA == 0L) pointer++
    } while (pointer <= program.lastIndex)

    println(output.joinToString(","))
}
