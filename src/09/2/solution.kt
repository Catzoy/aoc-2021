package `09`.`2`

import readTest

val IntRange.size: Int get() = fold(0) { acc, _ -> acc + 1 }

fun List<List<Int>>.findBasinOfRange(lineI: Int, basin: IntRange, step: (Int) -> Int): Int {
    val i = step(lineI)
    val line = getOrNull(i) ?: return 0
    val leftNineIndex =
        if (line[basin.first] == 9) basin.firstOrNull { line[it] != 9 }?.minus(1) ?: return 0
        else (basin.first downTo 0).firstOrNull { line[it] == 9 } ?: -1
    val rightNineIndex =
        if (line[basin.last] == 9) basin.reversed().firstOrNull { line[it] != 9 }?.plus(1) ?: return 0
        else (basin.last until line.size).firstOrNull { line[it] == 9 } ?: line.size
    if (leftNineIndex == rightNineIndex) return 0

    val basinRange = (leftNineIndex + 1) until rightNineIndex
    if (basinRange.all { line[it] == 9 }) return 0

    val ninesInRange = basinRange.filter { line[it] == 9 }
    val basinRanges = ninesInRange.fold(basinRange to mutableListOf<IntRange>()) { (range, acc), j ->
        val left = range.first until j
        val right = j + 1..range.last
        acc.add(left)
        right to acc
    }.let { (endRange, basinRanges) ->
        basinRanges.add(endRange); basinRanges
    }

    return basinRanges.sumOf { range -> range.size + findBasinOfRange(i, range, step) }
}

fun solve(input: List<String>) {
    val points = input.map { line -> line.toCharArray().map(Char::digitToInt) }
    val maxBasins = mutableListOf<Int>()
    for ((i, line) in points.withIndex()) {
        for ((j, point) in line.withIndex()) {
            val isLessThanTop = if (i != 0) point < points[i - 1][j] else true
            val isLessThanBottom = if (i != points.lastIndex) point < points[i + 1][j] else true
            val isLessThanLeft = if (j != 0) point < points[i][j - 1] else true
            val isLessThanRight = if (j != line.lastIndex) point < points[i][j + 1] else true
            if (isLessThanTop && isLessThanRight && isLessThanBottom && isLessThanLeft) {
                val leftNineIndex = (j downTo 0).firstOrNull { line[it] == 9 } ?: -1
                val rightNineIndex = (j until line.size).firstOrNull { line[it] == 9 } ?: line.size
                val basinRange = (leftNineIndex + 1) until rightNineIndex
                val topSize =
                    if (i != 0) points.findBasinOfRange(i, basinRange) { index -> index - 1 }
                    else 0
                val bottomSize =
                    if (i != points.lastIndex) points.findBasinOfRange(i, basinRange) { index -> index + 1 }
                    else 0
                val size = basinRange.size + topSize + bottomSize
                println("BASIN $basinRange AT $i,$j SIZED $size")
                maxBasins.add(size)
                if (maxBasins.size > 3) {
                    maxBasins.sort()
                    maxBasins.removeAt(0)
                }
            }
        }
    }
    println(maxBasins.joinToString(" | "))
    println(maxBasins.fold(1) { acc, b -> acc * b })
}

fun main() {
    solve(readTest("09"))
}