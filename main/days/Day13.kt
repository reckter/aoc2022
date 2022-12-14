package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.days.Day13.Item.ListItem
import me.reckter.aoc.days.Day13.Item.NumberItem
import me.reckter.aoc.solution
import me.reckter.aoc.solve
import me.reckter.aoc.splitAtEmptyLine

class Day13 : Day {
    override val day = 13

    sealed class Item() : Comparable<Item> {
        data class ListItem(val items: List<Item>) : Item() {
            override fun compareTo(other: Item): Int {
                return when (other) {
                    is ListItem -> items.zip(other.items).map { it.first.compareTo(it.second) }
                        .firstOrNull { it != 0 }
                        ?: items.size.compareTo(other.items.size)

                    is NumberItem -> this.compareTo(ListItem(listOf(other)))
                }
            }
        }

        data class NumberItem(val number: Int) : Item() {
            override fun compareTo(other: Item): Int {
                return when (other) {
                    is ListItem -> ListItem(listOf(this)).compareTo(other)
                    is NumberItem -> number.compareTo(other.number)
                }
            }
        }
    }

    fun parseList(input: String): Pair<ListItem, String> {
        val items = mutableListOf<Item>()
        var rest = input
        var finished = false
        while (rest.isNotEmpty() && !finished) {
            val (item, newRest) = when (rest.first()) {
                '[' -> parseList(rest.drop(1))
                ',' -> null to rest.drop(1)
                ']' -> {
                    finished = true
                    null to rest.drop(1)
                }

                else -> {
                    val numberStr = rest.takeWhile { it.isDigit() }
                    NumberItem(numberStr.toInt()) to rest.drop(numberStr.length)
                }
            }
            if (item != null) {
                items.add(item)
            }
            rest = newRest
        }
        return ListItem(items) to rest
    }

    override fun solvePart1() {
        loadInput(trim = false)
            .splitAtEmptyLine()
            .map {
                it.map {
                    parseList(it).first
                }
            }
            .mapIndexed { index, it -> (index + 1) to it.first().compareTo(it[1]) }
            .filter { it.second == -1 }
            .sumOf { it.first }
            .solution(1)
    }

    override fun solvePart2() {
        val divider1 = parseList("[[2]]").first
        val divider2 = parseList("[[6]]").first

        val list = loadInput()
            .filter { it.isNotBlank() }
            .map {
                parseList(it).first
            }
            .let { it + listOf(divider1, divider2) }
            .sorted()

        val divider1Index = list.indexOf(divider1)
        val divider2Index = list.indexOf(divider2)

        ((divider1Index + 1) * (divider2Index + 1)).solution(2)
    }
}

fun main() = solve<Day13>()
