package `06`.`2`

import readFull
import readTest

fun solve(input: List<String>) {
    val ticks = input[0].split(",").map { it.toInt() }.toMutableList()
    val pool = ticks.fold(
        mutableMapOf(
            0 to 0L, 1 to 0L, 2 to 0L, 3 to 0L, 4 to 0L,
            5 to 0L, 6 to 0L, 7 to 0L, 8 to 0L
        )
    ) { map, i ->
        map.apply { merge(i, 1L) { prev, upd -> prev + upd } }
    }
    for (i in 0 until 256) {
        val readyToBirth = pool.getValue(0)
        for (key in pool.keys.filterNot { it == 0 }) {
            pool[key - 1] = pool.getValue(key)
        }
        pool[8] = readyToBirth
        pool.merge(6, readyToBirth) { prev, upd -> prev + upd }
    }
    println(pool.values.sum())
}

fun main() {
    solve(readTest("06"))
    solve(readFull("06"))
}