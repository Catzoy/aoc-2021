package `03`.`1`

import java.io.File

fun main() {
    val input = File("src/03", "input_full.txt").readLines()
    val timesZeroToTimesOne = input.fold(mutableMapOf<Int, Pair<Int, Int>>()) { acc, bin ->
        bin.forEachIndexed { index, c ->
            acc[index] = acc.getOrElse(index) { 0 to 0 }.run {
                copy(
                    first = first + if (c == '0') 1 else 0,
                    second = second + if (c == '1') 1 else 0,
                )
            }
        }
        acc
    }
    val (gamma, epsilon) = timesZeroToTimesOne.keys.sorted().fold("" to "") { (gamma, epsilon), key ->
        val (zeroes, ones) = timesZeroToTimesOne.getValue(key)
        if (zeroes > ones) "${gamma}0" to "${epsilon}1"
        else "${gamma}1" to "${epsilon}0"
    }.let { (gamma, epsilon) ->
        gamma.toInt(radix = 2) to epsilon.toInt(radix = 2)
    }
    println("$gamma | $epsilon | ${gamma * epsilon}")
}