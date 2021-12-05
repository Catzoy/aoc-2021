package `05`.`1`

import readFull
import readTest

enum class LineType { VERTICAL, HORIZONTAL }
data class Point(val x: Int, val y: Int)
data class Line(val start: Point, val end: Point) {
    val type = if (start.x == end.x) LineType.VERTICAL else LineType.HORIZONTAL

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
val Line.yRange get() = if (start.y > end.y) start.y downTo end.y else start.y..end.y

fun solve(input: List<String>) {
    val lines = input.asSequence().map(Line::from).filter { it.start.x == it.end.x || it.start.y == it.end.y }
    val mapKeyBuilder = { x: Int, y: Int -> "$x,$y" }
    val tubeMap = lines.fold(mutableMapOf<String, Int>()) { map, line ->
        val (pointsRange, lineKeyBuilder) = when (line.type) {
            LineType.HORIZONTAL -> line.xRange to { i: Int -> mapKeyBuilder(i, line.start.y) }
            LineType.VERTICAL -> line.yRange to { i: Int -> mapKeyBuilder(line.start.x, i) }
        }
        for (i in pointsRange) {
            map.merge(lineKeyBuilder(i), 1) { value, upd -> upd + value }
        }
        map
    }
    println(tubeMap.values.count { it > 1 })
}

fun main() {
    solve(readTest("05"))
    solve(readFull("05"))
}