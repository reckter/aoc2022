package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.solution
import me.reckter.aoc.solve

class Day8 : Day {
    override val day = 8

    val map by lazy {
        loadInput()
            .map { row -> row.toList().map { it.toString().toInt() } }
    }

    fun checkVisibilityDirection(x: Int, y: Int, height: Int, dx: Int, dy: Int): Boolean {
        return generateSequence(x to y) { (x, y) -> (x + dx) to (y + dy) }
            .drop(1)
            .takeWhile { (x, y) -> x in 0 until map.first().size && y in map.indices }
            .none { (x, y) -> map[y][x] >= height }
    }

    fun checkVisibility(x: Int, y: Int): Boolean {
        val height = map[y][x]
        return checkVisibilityDirection(x, y, height, -1, 0) ||
            checkVisibilityDirection(x, y, height, 1, 0) ||
            checkVisibilityDirection(x, y, height, 0, -1) ||
            checkVisibilityDirection(x, y, height, 0, 1)
    }

    fun countVisibleTreesDirection(x: Int, y: Int, height: Int, dx: Int, dy: Int): Int {
        val trees = generateSequence(x to y) { (x, y) -> (x + dx) to (y + dy) }
            .drop(1)
            .takeWhile { (x, y) -> x in 0 until map.first().size && y in map.indices }
            .takeWhile { (x, y) -> map[y][x] < height }
            .toList()

        return if (trees.isEmpty() || trees.last().let {
            it.first == 0 || it.first == map.size - 1 || it.second == 0 || it.second == map.first().size - 1
        }
        ) {
            trees.count()
        } else {
            trees.count() + 1
        }
    }

    fun scenicScore(x: Int, y: Int): Int {
        val height = map[y][x]
        return countVisibleTreesDirection(x, y, height, -1, 0) *
            countVisibleTreesDirection(x, y, height, 1, 0) *
            countVisibleTreesDirection(x, y, height, 0, -1) *
            countVisibleTreesDirection(x, y, height, 0, 1)
    }

    override fun solvePart1() {
        map
            .mapIndexed { y, row ->
                row
                    .mapIndexed { x, tree ->
                        checkVisibility(x, y)
                    }
                    .count { it }
            }
            .sum()
            .solution(1)
    }

    override fun solvePart2() {
        map
            .mapIndexed { y, row ->
                row
                    .mapIndexed { x, tree ->
                        scenicScore(x, y)
                    }
                    .max()
            }
            .max()
            .solution(2)
    }
}

fun main() = solve<Day8>()
