package `10`.`2`

import runTests

fun solve(input: List<String>) {
    val scores = mapOf(
        '(' to 1,
        '[' to 2,
        '{' to 3,
        '<' to 4,
    )
    val incompleteLines = mutableListOf<List<Char>>()
    for (line in input) {
        val open = mutableListOf<Char>()
        fun check(expected: Char): Boolean {
            return if (open.last() == expected) {
                open.removeLast()
                false
            } else {
                true
            }
        }

        var foundIllegal = false
        for (char in line) {
            foundIllegal = when (char) {
                ')' -> check('(')
                ']' -> check('[')
                '}' -> check('{')
                '>' -> check('<')
                else -> {
                    open.add(char)
                    false
                }
            }
            if (foundIllegal) {
                break
            }
        }
        if (foundIllegal) continue

        incompleteLines.add(open.asReversed())
    }
    val linesScores = incompleteLines.map { line ->
        line.fold(0L) { acc, c ->
            acc * 5 + scores.getOrDefault(c, 0)
        }
    }.sorted()
    println("${linesScores[linesScores.size / 2]}")
}

fun main() {
    runTests("10", ::solve)
}