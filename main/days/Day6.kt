package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.solution
import me.reckter.aoc.solve

class Day6 : Day {
    override val day = 6

    override fun solvePart1() {
        loadInput()
            .first()
            .windowed(4, 1)
            .indexOfFirst { it.toList().distinct().size == 4 }
            .let { it + 4 }
            .solution(1)
    }

    override fun solvePart2() {
        loadInput()
            .first()
            .windowed(14, 1)
            .indexOfFirst { it.toList().distinct().size == 14 }
            .let { it + 14 }
            .solution(2)
    }
}

fun main() = solve<Day6>()
