package `07`.`1`

import readFull
import readTest
import kotlin.math.absoluteValue

fun solve(input: List<String>) {
    val positions = input[0].split(",").asSequence().map { it.toInt() }.sorted()
    val (position, fuel) =(0..positions.last()).fold(-1 to Int.MAX_VALUE) { positionToFuel, i ->
        val fuelToChange = positions.sumOf { pos -> (pos - i).absoluteValue }
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