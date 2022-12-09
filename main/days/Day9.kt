package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.cords.d2.Cord2D
import me.reckter.aoc.cords.d2.plus
import me.reckter.aoc.solution
import me.reckter.aoc.solve
import java.lang.Integer.max
import java.lang.Integer.min

class Day9 : Day {
    override val day = 9

    fun nextKnotPosition(head: Cord2D<Int>, tail: Cord2D<Int>): Cord2D<Int> {
        val newTail = tail + Cord2D(
            min(1, max(-1, head.x - tail.x)),
            min(1, max(-1, head.y - tail.y))
        )

        return if (newTail == head) tail else newTail
    }
    override fun solvePart1() {
        val map = mutableMapOf<Cord2D<Int>, Int>()
        map[Cord2D(0, 0)] = 1
        loadInput()
            .flatMap {
                val (direction, times) = it.split(" ")
                (0 until times.toInt()).map { direction }
            }
            .fold(Cord2D(0, 0) to Cord2D(0, 0)) { acc, cur ->
                val (head, tail) = acc
                val newHead = when (cur) {
                    "U" -> head + Cord2D(0, 1)
                    "D" -> head + Cord2D(0, -1)
                    "L" -> head + Cord2D(-1, 0)
                    "R" -> head + Cord2D(1, 0)
                    else -> error("invalid direction")
                }

                val newTail = nextKnotPosition(head, tail)

                map.compute(newTail) { _, v -> (v ?: 0) + 1 }

                newHead to newTail
            }

        map.values.count().solution(1)
    }

    override fun solvePart2() {
        val map = mutableMapOf<Cord2D<Int>, Int>()
        map[Cord2D(0, 0)] = 1
        loadInput()
            .flatMap {
                val (direction, times) = it.split(" ")
                (0 until times.toInt()).map { direction }
            }
            .fold((0..9).map { Cord2D(0, 0) }) { acc, cur ->
                val head = acc.first()
                val newHead = when (cur) {
                    "U" -> head + Cord2D(0, 1)
                    "D" -> head + Cord2D(0, -1)
                    "L" -> head + Cord2D(-1, 0)
                    "R" -> head + Cord2D(1, 0)
                    else -> error("invalid direction")
                }

                val newRope = acc.drop(1).runningFold(newHead) { acc, cur ->
                    nextKnotPosition(acc, cur)
                }

                map.compute(newRope.last()) { _, v -> (v ?: 0) + 1 }
                newRope
            }

        map.values.count().solution(2)
    }

    fun printRope(rope: List<Cord2D<Int>>) {
        val minX = rope.minBy { it.x }!!.x
        val maxX = rope.maxBy { it.x }!!.x
        val minY = rope.minBy { it.y }!!.y
        val maxY = rope.maxBy { it.y }!!.y

        println()
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val cord = Cord2D(x, y)
                if (cord in rope) {
                    when (val index = rope.indexOf(cord)) {
                        0 -> print("H")
                        rope.size - 1 -> print("T")
                        else -> print(index)
                    }
                } else {
                    print(".")
                }
            }
            println()
        }
    }
}

fun main() = solve<Day9>()
