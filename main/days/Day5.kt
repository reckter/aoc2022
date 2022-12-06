package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.parseWithRegex
import me.reckter.aoc.solution
import me.reckter.aoc.solve

class Day5 : Day {
	override val day = 5

	val startStacks by lazy {
		val picture = loadInput(trim = false)
			.takeWhile { it.isNotEmpty() }
		val height = picture.size
		val width = picture.last().length

		(0 until width)
			.map { x ->
				(height - 1 downTo 0)
					.map { y ->
						picture[y][x].toString()
					}
					.reduce { acc, c -> "$acc$c" }
			}
			.filter { it.first().isDigit() }
			.map { it.drop(1).trim() }
			.map { it.toList() }
	}

	val moves by lazy {
		loadInput(trim = false)
			.dropWhile { it.isNotEmpty() }
			.drop(1)
			.parseWithRegex("move (\\d+) from (\\d+) to (\\d+)")
			.map { (moves, from, to) -> Triple(moves.toInt(), from.toInt(), to.toInt()) }
	}

	override fun solvePart1() {
		val stacks = startStacks
			.map { it.toMutableList() }
		moves
			.fold(stacks) { acc, cur ->
				repeat(cur.first) {
					val card = acc[cur.second - 1].removeLast()
					acc[cur.third - 1].add(card)
				}
				acc
			}
			.map { it.last().toString() }
			.reduce { acc, c -> "$acc$c" }
			.solution(1)
	}

	override fun solvePart2() {
		val stacks = startStacks
			.map { it.toMutableList() }
			.toMutableList()
		moves
			.fold(stacks) { acc, cur ->
				val cards = acc[cur.second - 1].takeLast(cur.first)
				acc[cur.third - 1].addAll(cards)
				acc[cur.second - 1] = acc[cur.second - 1].dropLast(cur.first).toMutableList()
				acc
			}
			.map { it.last().toString() }
			.reduce { acc, c -> "$acc$c" }
			.solution(2)
	}
}

fun main() = solve<Day5>()
