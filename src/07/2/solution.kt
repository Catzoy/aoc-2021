package `07`.`2`

import readFull
import readTest
import kotlin.math.absoluteValue

fun crabDistance(pos: Int, i: Int): Int {
    val diff = (pos - i).absoluteValue
    if (diff == 0) return 0
    return diff * (1 + diff) / 2
}

fun solve(input: List<String>) {
    val positions = input[0].split(",").asSequence().map { it.toInt() }.sorted()
    val (position, fuel) =(0..positions.last()).fold(-1 to Int.MAX_VALUE) { positionToFuel, i ->
        val fuelToChange = positions.sumOf { pos -> crabDistance(pos, i) }
        if (fuelToChange < positionToFuel.second) {
            return@fold i to fuelToChange
        }
        return@fold positionToFuel
    }
    println("$position, $fuel")
}

fun main() {
    solve(readTest("07"))
    solve(readFull("07"))
}