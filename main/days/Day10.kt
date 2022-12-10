package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.solution
import me.reckter.aoc.solve

class Day10 : Day {
    override val day = 10

    override fun solvePart1() {
        loadInput()
            .flatMap {
                if (it.startsWith("addx")) listOf("noop", it) else listOf(it)
            }
            .runningFold(1) { x, instruction ->
                when (instruction.substringBefore(" ")) {
                    "noop" -> x
                    "addx" -> x + instruction.substringAfter(" ").toInt()
                    else -> error("invalid instruction")
                }
            }
            .mapIndexed { index, i -> i * (index + 1) }
            .filterIndexed { index, i -> (index + 1) in listOf(20, 60, 100, 140, 180, 220) }
            .sum()
            .solution(1)
    }

    override fun solvePart2() {
        loadInput()
            .flatMap {
                if (it.startsWith("addx")) listOf("noop", it) else listOf(it)
            }
            .asSequence()
            .runningFold(1) { x, instruction ->
                when (instruction.substringBefore(" ")) {
                    "noop" -> x
                    "addx" -> x + instruction.substringAfter(" ").toInt()
                    else -> error("invalid instruction")
                }
            }
            .mapIndexed { index, i -> (index) to i }
            .map { (index, i) ->
                if (i - (index % 40) in -1..1) "#" else " "
            }
            .windowed(40, 40)
            .joinToString("\n") {
                it.joinToString("")
            }
            .let { "\n$it" }
            .solution(2)
    }
}

fun main() = solve<Day10>()
