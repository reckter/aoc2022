package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.cords.d2.Cord2D
import me.reckter.aoc.cords.d2.getNeighbors
import me.reckter.aoc.dijkstraInt
import me.reckter.aoc.solution
import me.reckter.aoc.solve

class Day12 : Day {
    override val day = 12

    val rawMap by lazy {
        loadInput().mapIndexed { y, row -> row.mapIndexed { x, c -> Cord2D(x, y) to c } }.flatten().toMap()
    }

    val heightMap by lazy {
        rawMap.mapValues { (_, it) ->
            if (it == 'S') 0 else if (it == 'E') 25 else it - 'a'
        }
    }

    val startPoint by lazy { rawMap.entries.find { it.value == 'S' }!!.key }
    val endPoint by lazy { rawMap.entries.find { it.value == 'E' }!!.key }

    override fun solvePart1() {
        dijkstraInt(startPoint, endPoint, { node ->
            val height = heightMap[node]!!
            node.getNeighbors(true)
                .filter { it in heightMap }
                .filter {
                    heightMap[it]!! - height <= 1
                }
        }, { a, b -> 1 })
            .second
            .solution(1)
    }

    override fun solvePart2() {
        dijkstraInt(
            start = endPoint,
            isEnd = { heightMap[it]!! == 0 },
            getNeighbors = { node ->
                val height = heightMap[node]!!
                node.getNeighbors(true)
                    .filter { it in heightMap }
                    .filter {
                        height - heightMap[it]!! <= 1
                    }
            },
            getWeightBetweenNodes = { a, b -> 1 }
        )
            .second
            .solution(2)
// 		heightMap
// 			.filter { it.value == 0 }
// 			.mapNotNull {
// 				try {
// 					dijkstraInt(it.key, endPoint, { node ->
// 						val height = heightMap[node]!!
// 						node.getNeighbors(true)
// 							.filter { it in heightMap }
// 							.filter {
// 								heightMap[it]!! - height <= 1
// 							}
// 					}, { a, b -> 1 })
// 						.second
// 				} catch (_: Exception) {
// 					null
// 				}
// 			}
// 			.min()
// 			.solution(2)
    }
}

fun main() = solve<Day12>()
