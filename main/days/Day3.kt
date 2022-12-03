package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.print
import me.reckter.aoc.solution
import me.reckter.aoc.solve

class Day3 : Day {
	override val day = 3

	override fun solvePart1() {
		loadInput()
			.map {
				val first = it.substring(0, it.length / 2)
				val second = it.substring(it.length / 2)
				val inBoth = first.find { it in second }!!
				if(inBoth - 'a' > 0) inBoth - 'a' + 1 else inBoth - 'A' + 27
			}
			.sum()
			.solution(1)
	}

	override fun solvePart2() {
		loadInput()
			.windowed(3,3)
			.map {
				val inBoth = it.reduce { cur, acc -> cur.filter { it in acc }}[0]

				if(inBoth - 'a' > 0) inBoth - 'a' + 1 else inBoth - 'A' + 27
			}
			.sum()
			.solution(2)

	}
}

fun main() = solve<Day3>()
