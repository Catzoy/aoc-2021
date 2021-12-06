package `06`.`1`

import readFull
import readTest

fun solve(input: List<String>) {
    val ticks = input[0].split(",").map { it.toInt() }.toMutableList()
    for (i in 0 until 24) {
        var eights = 0
        for (index in ticks.indices) {
            var tick = ticks[index]
            if (tick == 0) {
                eights += 1
                tick = 7
            }
            tick -= 1
            ticks[index] = tick
        }
        ticks.addAll(List(eights) { 8 })
    }
    println(ticks.size)
}

fun main() {
    solve(readTest("06"))
    solve(readFull("06"))
}