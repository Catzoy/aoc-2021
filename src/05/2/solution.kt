package `05`.`2`

import readFull
import readTest

enum class LineType { VERTICAL, HORIZONTAL, DIAGONAL }
data class Point(val x: Int, val y: Int)
data class Line(val start: Point, val end: Point) {
    val type = when {
        start.x == end.x -> LineType.VERTICAL
        start.y == end.y -> LineType.HORIZONTAL
        else -> LineType.DIAGONAL
    }

    companion object {
        private val lineRegex = Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)")
        fun from(str: String): Line {
            val points = lineRegex.findAll(str).first().groupValues.drop(1).map { it.toInt() }
            return Line(
                start = Point(x = points[0], y = points[1]),
                end = Point(x = points[2], y = points[3])
            )
        }
    }
}

val Line.xRange get() = if (start.x > end.x) start.x downTo end.x else start.x..end.x
val Line.xPoints get() = xRange.asSequence().map { x -> x to start.y }

val Line.yRange get() = if (start.y > end.y) start.y downTo end.y else start.y..end.y
val Line.yPoints get() = yRange.asSequence().map { y -> start.x to y }

val Line.xyPoints get() = xRange.zip(yRange).asSequence()

fun solve(input: List<String>) {
    val lines = input.asSequence().map(Line::from)
    val mapKeyBuilder = { x: Int, y: Int -> "$x,$y" }
    val tubeMap = lines.fold(mutableMapOf<String, Int>()) { map, line ->
        val pointsRange = when (line.type) {
            LineType.HORIZONTAL -> line.xPoints
            LineType.VERTICAL -> line.yPoints
            LineType.DIAGONAL -> line.xyPoints
        }
        for ((x, y) in pointsRange) {
            map.merge(mapKeyBuilder(x, y), 1) { value, upd -> upd + value }
        }
        map
    }
    println(tubeMap.values.count { it > 1 })
}

fun main() {
    solve(readTest("05"))
    solve(readFull("05"))
}