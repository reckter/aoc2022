package me.reckter.aoc.days

import me.reckter.aoc.Day
import me.reckter.aoc.days.Day7.Item.File
import me.reckter.aoc.days.Day7.Item.Folder
import me.reckter.aoc.solution
import me.reckter.aoc.solve

class Day7 : Day {
    override val day = 7

    sealed class Item(
        open val name: String,
        open val parent: Folder? = null
    ) {

        abstract fun getSizeOnDisk(): Int
        data class Folder(
            val children: MutableList<Item> = mutableListOf(),
            override val name: String,
            override val parent: Folder? = null
        ) : Item(name, parent) {
            override fun getSizeOnDisk(): Int {
                return children.sumOf { it.getSizeOnDisk() }
            }

            fun getAllFolders(): Sequence<Folder> {
                return sequence {
                    yield(this@Folder)
                    children.forEach {
                        if (it is Folder) {
                            yieldAll(it.getAllFolders())
                        }
                    }
                }
            }
        }

        data class File(
            val size: Int,
            override val name: String,
            override val parent: Folder? = null
        ) : Item(name, parent) {
            override fun getSizeOnDisk(): Int = size
        }
    }

    val root by lazy {
        val input = loadInput().toMutableList()
        val root = Folder(name = "/")
        var current = root
        while (input.isNotEmpty()) {
            val line = input.removeAt(0)
            when {
                line.startsWith("$ cd") -> {
                    val folder = line.substringAfter("$ cd ")
                    if (folder == "..") {
                        current = current.parent ?: error("no parent")
                        continue
                    }
                    if (folder == "/") {
                        current = root
                        continue
                    }
                    var existingFolder = current.children.find { it.name == folder } as Folder?

                    if (existingFolder == null) {
                        existingFolder = Folder(name = folder, parent = current)
                        current.children.add(existingFolder ?: error("folder should not be null"))
                    }
                    current = existingFolder ?: error("folder should not be null")
                }

                line.startsWith("$ ls") -> {
                    val result = input.takeWhile { !it.startsWith("$") }
                    repeat(result.size) { input.removeAt(0) }
                    result.forEach {
                        val name = it.substringAfterLast(" ")
                        val size = it.substringBefore(" ")

                        if (size == "dir") {
                            var existingFolder = current.children.find { it.name == name } as Folder?
                            if (existingFolder == null) {
                                existingFolder = Folder(name = name, parent = current)
                                current.children.add(existingFolder ?: error("folder is null"))
                            }
                        } else {
                            current.children.add(File(size.toInt(), name, current))
                        }
                    }
                }
            }
        }
        root
    }

    override fun solvePart1() {
        root.getAllFolders()
            .map { it.getSizeOnDisk() }
            .filter { it < 100000 }
            .sum()
            .solution(1)
    }

    override fun solvePart2() {
        val maxSpaceOnDisk = 70000000
        val neededSpace = 30000000
        val availableSpace = maxSpaceOnDisk - root.getSizeOnDisk()
        val additionalNeededSpace = neededSpace - availableSpace

        root.getAllFolders()
            .map { it.getSizeOnDisk() }
            .filter { it > additionalNeededSpace }
            .min()
            .solution(2)
    }
}

fun main() = solve<Day7>()
