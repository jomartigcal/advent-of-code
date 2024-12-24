// Day 24: Crossed Wires
// https://adventofcode.com/2024/day/24

import java.io.File

fun main() {
    val lines = File("src/main/resources/Day2x.txt").readText()
    val (gates, connections) = lines.split("\n\n").map { it.split("\n") }

    val gateValues = gates.associate {
        val (gate, value) = it.split(": ")
        gate to (value == "1")
    }.toMutableMap()

    val regex = """(\w*) (\w*) (\w*) -> (\w*)""".toRegex()
    val gateConnections = connections.mapNotNull {
        regex.matchEntire(it)?.destructured?.let { connection ->
            val (operand1, operator, operand2, gate) = connection
            gate to Triple(operand1, operator, operand2)
        }
    }.toMutableList()

    while (true) {
        val iterator = gateConnections.listIterator()
        while (iterator.hasNext()) {
            val connection = iterator.next()
            val operation = connection.second
            if (gateValues.get(operation.first) != null && gateValues.get(operation.third) != null) {
                gateValues.put(
                    connection.first,
                    operateGate(
                        gateValues.getOrDefault(operation.first, false),
                        operation.second,
                        gateValues.getOrDefault(operation.third, false),
                    )
                )
                iterator.remove()
            }
        }
        if (iterator.hasNext().not() && iterator.hasPrevious().not()) {
            break
        }
    }

    gateValues.toSortedMap().filter { it.key.startsWith("z") }
        .values.map { if (it) 1 else 0 }.joinToString("").reversed()
        .also { println(it.toLong(2)) }

}

private fun operateGate(operand1: Boolean, operator: String, operand2: Boolean): Boolean {
    return when (operator) {
        "AND" -> operand1 && operand2
        "OR" -> operand1 || operand2
        "XOR" -> operand1 xor operand2
        else -> throw Exception("Unknown Operand")
    }
}
