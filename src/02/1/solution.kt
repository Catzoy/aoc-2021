package `02`.`1`

import java.io.File

fun main() {
    val input = File("src/02/input_full.txt").readLines()
    val commands = input.asSequence().map { it.split(" ") }.map { it[0] to it[1].toInt() }
    val (position, depth) = commands.fold(0 to 0) { (position, depth), (cmd, value) ->
        when(cmd) {
            "forward" -> position + value to depth
            "down" -> position to depth + value
            "up" -> position to depth - value
            else -> throw Exception("Unsupported operation $cmd $value")
        }
    }
    println(position * depth)
}