package `09`.`1`

import runTests

fun solve(input: List<String>) {
    val points = input.map { line -> line.toCharArray().map(Char::digitToInt) }
    val scoreOfLowest = points.withIndex().sumOf { (i, line) ->
        line.withIndex().sumOf { (j, point) ->
            val isLessThanTop = if (i != 0) point < points[i - 1][j] else true
            val isLessThanBottom = if (i != points.lastIndex) point < points[i + 1][j] else true
            val isLessThanLeft = if (j != 0) point < points[i][j - 1] else true
            val isLessThanRight = if (j != line.lastIndex) point < points[i][j + 1] else true
            if (isLessThanTop && isLessThanRight && isLessThanBottom && isLessThanLeft) point + 1 else 0
        }
    }
    println(scoreOfLowest)
}

fun main() {
    runTests("09", ::solve)
}