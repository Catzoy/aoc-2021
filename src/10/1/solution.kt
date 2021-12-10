package `10`.`1`

import runTests

fun solve(input: List<String>) {
    val scores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137,
    )
    val illegalChars = mutableListOf<Char>()
    for (line in input) {
        val open = mutableListOf<Char>()
        fun check(char: Char, expected: Char): Boolean {
            return if (open.last() == expected) {
                open.removeLast()
                false
            } else {
                illegalChars.add(char)
                true
            }
        }
        for (char in line) {
            val foundIllegal = when (char) {
                ')' -> check(char, '(')
                ']' -> check(char, '[')
                '}' -> check(char, '{')
                '>' -> check(char, '<')
                else -> {
                    open.add(char)
                    false
                }
            }
            if (foundIllegal) {
                break
            }
        }
    }
    val score = illegalChars.sumOf { c ->
        scores.getOrDefault(c, 0)
    }
    println(score)
}

fun main() {
    runTests("10", ::solve)
}