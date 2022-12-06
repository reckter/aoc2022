package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.parseWithRegex
import me.reckter.aoc.solution
import me.reckter.aoc.solve

class Day4 : Day {
	override val day = 4

	val parsed by lazy {
		loadInput()
			.map {
				val list = it.split(",")
					.parseWithRegex("(\\d+)-(\\d+)")
					.map { (from, to) -> from.toInt()..to.toInt() }
					.sortedBy { it.first * 1000 - it.last }

				if (list.size != 2) error("invalid list length")
				list.first() to list.last()
			}
	}

	override fun solvePart1() {
		parsed
			.count {
				val (first, second) = it
				first.contains(second.first) && first.contains(second.last)
			}
			.solution(1)

	}

	override fun solvePart2() {
		parsed
			.count {
				val (first, second) = it
				first.contains(second.first) || first.contains(second.last) || second.contains(first.first) || second.contains(first.last)
			}
			.solution(2)
	}
}

fun main() = solve<Day4>()
