package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.solution
import me.reckter.aoc.solve
import me.reckter.aoc.splitAtEmptyLine
import java.math.BigInteger

class Day11 : Day {
    override val day = 11

    data class Monkey(
        val id: Int,
        val items: MutableList<BigInteger>,
        val operation: (BigInteger) -> BigInteger,
        val test: Test,
        var inspectedItems: BigInteger = BigInteger.ZERO
    ) {
        data class Test(
            val divider: BigInteger,
            val trueCase: Int,
            val falseCase: Int
        )
    }

    val startMonkeys by lazy {
        loadInput(trim = false)
            .splitAtEmptyLine()
            .map {
                val id = "Monkey (\\d+):".toRegex().matchEntire(it.first())!!.groups[1]!!.value.toInt()
                val items = it.drop(1).first().split(": ").last().split(", ").map { it.toBigInteger() }.toMutableList()
                val operation = it.drop(2).first().split(": ").last().let {
                    val op = it.split(" ").dropLast(1).last()
                    val argument = it.split(" ").last()
                    when (argument) {
                        "old" -> when (op) {
                            "*" -> { x: BigInteger -> x * x }
                            "+" -> { x: BigInteger -> x + x }
                            else -> error("invalid operation")
                        }

                        else -> when (op) {
                            "*" -> { x: BigInteger -> x * argument.toBigInteger() }
                            "+" -> { x: BigInteger -> x + argument.toBigInteger() }
                            else -> error("invalid operation")
                        }
                    }
                }
                val divider = it.drop(3).first().split("by ").last().toBigInteger()
                val trueCase = it.drop(4).first().split("monkey ").last().toInt()
                val falseCase = it.last().split("monkey ").last().toInt()
                Monkey(id, items, operation, Monkey.Test(divider, trueCase, falseCase))
            }
    }

    override fun solvePart1() {
        val monkeys = startMonkeys.map { it.copy(items = it.items.toMutableList()) }

        repeat(20) {
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    val worryAfterOperation = monkey.operation(item)
                    val worryAfterCool = worryAfterOperation / BigInteger.valueOf(3)
                    val tossToMonkey =
                        if (worryAfterCool.mod(monkey.test.divider) == BigInteger.ZERO) monkey.test.trueCase else monkey.test.falseCase
                    monkeys[tossToMonkey].items.add(worryAfterCool)
                    monkey.inspectedItems = monkey.inspectedItems + BigInteger.ONE
                }
                monkey.items.clear()
            }
        }
        monkeys
            .map { it.inspectedItems }
            .sortedDescending()
            .take(2)
            .reduce { acc, i -> acc * i }
            .solution(1)
    }

    override fun solvePart2() {
        val monkeys = startMonkeys.map { it.copy(items = it.items.toMutableList()) }

        val mod = monkeys.map { it.test.divider }.reduce { acc, bigInteger ->
            val mult = acc * bigInteger
            val gcd = acc.gcd(bigInteger)
            mult.divide(gcd)
        }

        repeat(10000) {
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    val worryAfterOperation = monkey.operation(item)
                    val worryAfterCool = worryAfterOperation.mod(mod)
                    val tossToMonkey =
                        if (worryAfterCool.mod(monkey.test.divider) == BigInteger.ZERO) monkey.test.trueCase else monkey.test.falseCase
                    monkeys[tossToMonkey].items.add(worryAfterCool)
                    monkey.inspectedItems = monkey.inspectedItems + BigInteger.ONE
                }
                monkey.items.clear()
            }
        }
        monkeys
            .map { it.inspectedItems }
            .sortedDescending()
            .take(2)
            .reduce { acc, i -> acc * i }
            .solution(2)
    }
}

fun main() = solve<Day11>()
