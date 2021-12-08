package `08`.`1`

import runTests

fun solve(input: List<String>) {
    val displayed = input.asSequence().map { it.split("|").last().split(" ") }
    val occurrences = displayed.fold(mutableMapOf<Int, Int>()) { acc, digits ->
        for (digit in digits) {
            val updatedNum = when (digit.length) {
                2 -> 1
                3 -> 7
                4 -> 4
                7 -> 8
                else -> continue
            }
            acc[updatedNum] = acc.getOrDefault(updatedNum, 0) + 1
        }
        acc
    }
    val occurrencesNum = occurrences.values.sum()
    println(occurrencesNum)
}

fun main() {
    runTests("08", ::solve)
}