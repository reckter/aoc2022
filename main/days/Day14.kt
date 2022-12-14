package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.cords.d2.Cord2D
import me.reckter.aoc.cords.d2.lineTo
import me.reckter.aoc.cords.d2.plus
import me.reckter.aoc.days.Day14.Entity.EMPTY
import me.reckter.aoc.parseWithRegex
import me.reckter.aoc.solution
import me.reckter.aoc.solve

class Day14 : Day {
    override val day = 14

    val startMap by lazy {
        loadInput()
            .flatMap {
                it
                    .split(" -> ")
                    .parseWithRegex("(\\d+),(\\d+)")
                    .map { (x, y) -> Cord2D(x.toInt(), y.toInt()) }
                    .zipWithNext()
                    .flatMap { (start, end) ->
                        start.lineTo(end)
                    }
            }
            .distinct()
            .associateWith { Entity.WALL }
    }

    val floor by lazy { startMap.keys.maxOf { it.y } }

    enum class Entity {
        EMPTY,
        WALL,
        SAND
    }

    fun fallSand(map: MutableMap<Cord2D<Int>, Entity>, withRealFloor: Boolean): Boolean {
        var current = Cord2D(500, 0)
        if (map.getOrDefault(current, EMPTY) != EMPTY) {
            return false
        }

        val floorLevel = if (withRealFloor) {
            floor + 1
        } else {
            floor
        }

        var changed = true
        while (changed && (current.y <= floorLevel)) {
            changed = false
            if (withRealFloor && current.y == floorLevel) {
                continue
            }
            if (map.getOrDefault(current.plus(Cord2D(0, 1)), EMPTY) == EMPTY) {
                current = current.plus(Cord2D(0, 1))
                changed = true
                continue
            }
            if (map.getOrDefault(current.plus(Cord2D(-1, 1)), EMPTY) == EMPTY) {
                current = current.plus(Cord2D(-1, 1))
                changed = true
                continue
            }
            if (map.getOrDefault(current.plus(Cord2D(1, 1)), EMPTY) == EMPTY) {
                current = current.plus(Cord2D(1, 1))
                changed = true
                continue
            }
        }

        return if (current.y > floorLevel) {
            false
        } else {
            map[current] = Entity.SAND
            true
        }
    }

    override fun solvePart1() {
        val map = startMap.toMutableMap()
        while (fallSand(map, false)) {
        }
        map.filterValues { it == Entity.SAND }.size.solution(1)
    }

    override fun solvePart2() {
        val map = startMap.toMutableMap()
        while (fallSand(map, true)) {
        }
        map.filterValues { it == Entity.SAND }.size.solution(2)
    }
}

fun main() = solve<Day14>()
