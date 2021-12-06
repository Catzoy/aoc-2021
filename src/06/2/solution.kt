package `06`.`2`

import readTest
import kotlin.math.pow

//0: 6

//7: 6, 8

//14: 6, 1, 8
//15: 5, 0, 7
//16: 4, 6, 6, 8

//20: 0, 2, 2, 4
//21: 6, 1, 1, 3, 8
//22: 5, 0, 0, 2, 7
//23: 4, 6, 6, 1, 6, 8, 8
//24: 3, 5, 5, 0, 5, 7, 7
//25: 2, 4, 4, 6, 4, 6, 6, 8

//28: 6, 1, 1, 3, 1, 3, 3, 5, 8
//29: 5, 0, 0, 2, 0, 2, 2, 4, 7
//30: 4, 6, 6, 1, 6, 1, 1, 3, 6, 8, 8, 8
//31: 3, 5, 5, 0, 5, 0, 0, 2, 5, 7, 7, 7
//32: 2, 4, 4, 6, 4, 6, 6, 1, 4, 6, 6, 6, 8, 8, 8
//33: 1, 3, 3, 5, 3, 5, 5, 0, 3, 5, 5, 5, 7, 7, 7
//34: 0, 2, 2, 4, 2, 4, 4, 6, 2, 4, 4, 4, 6, 6, 6, 8

// TODO: make formula for pattern above
fun factorial(n: Int): Long {
    if (n == 0) return 1
    return (1..n).fold(1) { acc, i -> acc * i }
}

fun Long.pow(i: Int): Long = this.toDouble().pow(i).toLong()

fun coef(n: Int, k: Int) = factorial(n) / (factorial(k) * factorial(n - k))

val DAYS_IN_WEEK = 7

fun solve(input: List<String>, days: Int) {
    val fish = input[0].split(",").map { it.toInt() }.toMutableList()
    val cycles = days / 6
    val n = cycles - 1
    val prev = 2L.pow(n)
    val posOffset = days - (cycles * 8)
    val perBirthMap = (0..7).fold(mutableMapOf<Int, Long>()) { map, i ->
        val position = posOffset + DAYS_IN_WEEK - i
        val coefs = (0..position).asSequence().map { coef(n, it) }.toList()
        val children = prev + coefs.sum()
        map[i] = children
        map
    }
    val perFishColony = fish.map { perBirthMap.getValue(it) }
    val sum = perFishColony.sum()
    println(perBirthMap.entries.joinToString("\n") { (k, v) -> "$k:$v" })
    println(perFishColony.joinToString(" | "))
    println("S $sum")
}

fun main() {
    solve(readTest("06"), 24)
//    solve(readFull("06"), 256)
}