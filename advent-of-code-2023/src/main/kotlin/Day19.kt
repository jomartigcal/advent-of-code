// Day 19: Aplenty
// https://adventofcode.com/2023/day/19

import java.io.File

private const val COLON = ":"
private const val GREATER_THAN = ">"
private const val LESS_THAN = "<"

data class Workflow(val name: String, val rules: List<String>)

data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {
    fun getValue(category: String): Int {
        return when (category) {
            "x" -> x
            "m" -> m
            "a" -> a
            "s" -> s
            else -> 0
        }
    }
}

fun main() {
    val lines = File("src/main/resources/Day19.txt").readLines()

    val workflowList = lines.takeWhile { it.isNotEmpty() }
    val workFlows = workflowList.map { workflow ->
        val rules = workflow.substringAfter("{").substringBefore("}")
        Workflow(
            workflow.substringBefore("{"),
            rules.split(",")
        )
    }

    val regex = """\{x=(\d*),m=(\d*),a=(\d*),s=(\d*)}""".toRegex()
    val partList = lines.drop(workflowList.size + 1).takeWhile { it.isNotEmpty() }
    val parts = partList.mapNotNull {
        regex.matchEntire(it)?.destructured?.let { xmas ->
            val (x, m, a, s) = xmas
            Part(x = x.toInt(), m = m.toInt(), a = a.toInt(), s = s.toInt())
        }
    }
    val sumOfAcceptedParts = parts.sumOf { part ->
        if (sortThroughParts(part, workFlows)) {
            part.x + part.m + part.a + part.s
        } else {
            0
        }
    }
    println(sumOfAcceptedParts)
    check(sumOfAcceptedParts == 397643)
}

private fun sortThroughParts(part: Part, workflows: List<Workflow>): Boolean {
    var workflow = workflows.find { it.name == "in" } ?: return false

    while (true) {
        val rules = workflow.rules
        for (index in rules.indices) {
            val rule = rules[index]
            if (isPartAccepted(rule)) {
                return true
            } else if (isPartRejected(rule)) {
                return false
            } else if (rule.contains(LESS_THAN) || rule.contains(GREATER_THAN)) {
                val symbol = if (rule.contains(LESS_THAN)) LESS_THAN else GREATER_THAN
                val left = part.getValue(rule.substringBefore(symbol))
                val right = rule.substringAfter(symbol).substringBefore(COLON).toInt()
                val result = rule.substringAfter(COLON)
                if (symbol == LESS_THAN && left < right || symbol == GREATER_THAN && left > right) {
                    if (isPartAccepted(result)) {
                        return true
                    } else if (isPartRejected(result)) {
                        return false
                    } else {
                        workflow = workflows.find { it.name == result } ?: return false
                        break
                    }
                }
            } else {
                workflow = workflows.find { it.name == rule } ?: return false
                break
            }
        }
    }
}

private fun isPartAccepted(value: String) = value == "A"

private fun isPartRejected(value: String) = value == "R"
