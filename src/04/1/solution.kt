package `04`.`1`

import readFull
import readTest

class Cell(val value: Int) {
    var marked = false

    override fun toString(): String {
        return "($value | $marked)"
    }
}

class Board(lines: List<String>) {
    private val rows = lines.map { line -> line.trim().split(Regex("\\s+")).map { Cell(it.toInt()) } }

    private fun markAllWith(num: Int) {
        for (row in rows) {
            for (cell in row) {
                cell.marked = cell.marked || cell.value == num
            }
        }
    }

    private fun anyRowMarked(): Boolean {
        return rows.any { row -> row.all { cell -> cell.marked } }
    }

    private fun anyColumnMarked(): Boolean {
        return rows.indices.any { index -> rows.asSequence().map { row -> row[index] }.all { cell -> cell.marked } }
    }

    fun mark(num: Int): Boolean {
        markAllWith(num)
        return anyRowMarked() || anyColumnMarked()
    }

    fun scoreWith(num: Int): Int {
        return num * rows.flatMap { row -> row.filterNot { it.marked } }.sumOf { it.value }
    }

    override fun toString(): String {
        return rows.joinToString("\n") { row -> row.joinToString(prefix = "[", postfix = "]") }
    }
}

fun solve(input:List<String>) {
    val drawnNums = input[0].split(",").asSequence().map { it.toInt() }
    val boards = input.asSequence().drop(1).filterNot { it.isEmpty() }.chunked(5) { Board(it) }.toList()
    loop@ for (drawnNum in drawnNums) {
        for (board in boards) {
            val hasWon = board.mark(drawnNum)
            if (hasWon) {
                println(board.scoreWith(drawnNum))
                break@loop
            }
        }
    }
}

fun main() {
    solve(readTest("04"))
    solve(readFull("04"))
}