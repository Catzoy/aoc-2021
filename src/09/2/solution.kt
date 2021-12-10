package `09`.`2`

import runTests

val IntRange.size: Int get() = fold(0) { acc, _ -> acc + 1 }

fun solve(input: List<String>) {
    val points = input.map { line -> line.toCharArray().map(Char::digitToInt) }
    val subNinesByLine = points.map { line ->
        val ranges = mutableListOf(line.indices)
        line.indices.filter { line[it] == 9 }.fold(ranges) { lineRanges, i ->
            val prev = lineRanges.removeLast()
            if (prev.first == i) {
                lineRanges.add(prev.first + 1..prev.last)
            } else {
                lineRanges.add(prev.first until i)
                lineRanges.add(i + 1..prev.last)
            }
            lineRanges
        }
    }
    val maxBasins = mutableListOf<Int>()
    for ((i, line) in points.withIndex()) {
        for ((j, point) in line.withIndex()) {
            val isLessThanTop = if (i != 0) point < points[i - 1][j] else true
            val isLessThanBottom = if (i != points.lastIndex) point < points[i + 1][j] else true
            val isLessThanLeft = if (j != 0) point < points[i][j - 1] else true
            val isLessThanRight = if (j != line.lastIndex) point < points[i][j + 1] else true
            if (isLessThanTop && isLessThanRight && isLessThanBottom && isLessThanLeft) {
                val basinRange = subNinesByLine[i].single { r -> j in r }
                val topBasinRanges = listOf(basinRange).countSubBasinsIn(subNinesByLine, i, -1)
                val bottomBasinRanges = listOf(basinRange).countSubBasinsIn(subNinesByLine, i, 1)
                val size = basinRange.size + topBasinRanges + bottomBasinRanges
                maxBasins.add(size)
                if (maxBasins.size > 3) {
                    maxBasins.sort()
                    maxBasins.removeAt(0)
                }
            }
        }
    }
    println(maxBasins.fold(1) { acc, score -> acc * score })
}

private fun List<IntRange>.countSubBasinsIn(subNinesByLine: List<List<IntRange>>, index: Int, step: Int): Int {
    val i = index + step
    val line = subNinesByLine.getOrNull(i) ?: return 0

    val effected = line.filter { range -> this.any { inner -> range.any { i -> i in inner } } }
    if (effected.isEmpty()) return 0

    return effected.sumOf { r -> r.size } + effected.countSubBasinsIn(subNinesByLine, i, step)
}

fun main() {
    runTests("09", ::solve)
}