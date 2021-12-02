package `02`.`2`

import java.io.File

fun main() {
    val input = File("src/02/input_full.txt").readLines()
    val commands = input.asSequence().map { it.split(" ") }.map { it[0] to it[1].toInt() }
    val (position, depth) = commands.fold(Triple(0, 0, 0)) { (position, depth, aim), (cmd, value) ->
        when (cmd) {
            "forward" -> Triple(position + value, depth + (aim * value), aim)
            "down" -> Triple(position, depth, aim + value)
            "up" -> Triple(position, depth, aim - value)
            else -> throw Exception("Unsupported operation $cmd $value")
        }
    }
    println(position * depth)
}