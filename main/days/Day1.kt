package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.solution
import me.reckter.aoc.solve
import me.reckter.aoc.splitAtEmptyLine
import me.reckter.aoc.toIntegers

class Day1 : Day {
    override val day = 1

    override fun solvePart1() {
        loadInput(trim = false)
            .splitAtEmptyLine()
            .map { it.toList().toIntegers() }
            .map { it.sum() }
            .max()
            .solution(1)
    }

    override fun solvePart2() {
        loadInput(trim = false)
            .splitAtEmptyLine()
            .map { it.toList().toIntegers() }
            .map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()
            .solution(2)
    }
}

fun main() = solve<Day1>()
