package `01`.`2`

import java.io.File

fun main() {
    val input = File("src/01/input_full.txt").readLines()
    val nums = input.asSequence().map { it.toInt() }
    val (numsIncreased, _) = nums.windowed(size = 3)
        .map { values -> values.sum() }
        .fold(-1 to 0) { (n, prev), sum ->
            (n + if (prev < sum) 1 else 0) to sum
        }
    println(numsIncreased)
}