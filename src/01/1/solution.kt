package `01`.`1`

import java.io.File

fun main() {
    val input = File("src/01/input_full.txt").readLines()
    val nums = input.asSequence().map { it.toInt() }
    val (numIncreased, _) = nums.fold(-1 to 0) { (n, prev), cur ->
        (n + (if (cur > prev) 1 else 0)) to cur
    }
    println(numIncreased)
}