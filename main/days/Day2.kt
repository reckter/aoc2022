package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.solution
import me.reckter.aoc.solve

class Day2 : Day {
    override val day = 2

    override fun solvePart1() {
        val points = mapOf(
            "A X" to 3 + 1,
            "B X" to 0 + 1,
            "C X" to 6 + 1,

            "A Y" to 6 + 2,
            "B Y" to 3 + 2,
            "C Y" to 0 + 2,

            "A Z" to 0 + 3,
            "B Z" to 6 + 3,
            "C Z" to 3 + 3
        )
        loadInput()
            .mapNotNull { points[it] }
            .sum()
            .solution(1)
    }

    override fun solvePart2() {
        val points = mapOf(
            "A X" to 3,
            "B X" to 1,
            "C X" to 2,

            "A Y" to 3 + 1,
            "B Y" to 3 + 2,
            "C Y" to 3 + 3,

            "A Z" to 6 + 2,
            "B Z" to 6 + 3,
            "C Z" to 6 + 1
        )
        loadInput()
            .mapNotNull { points[it] }
            .sum()
            .solution(2)
    }
}

fun main() = solve<Day2>()
