package `08`.`2`

import runTests

fun IntArray.isIn(other: IntArray): Boolean = all { c -> c in other }

fun IntArray.sameAs(other: IntArray): Boolean = this.size == other.size && this.isIn(other) && other.isIn(this)

fun List<IntArray>.findZeroSixNineBy(four: IntArray, seven: IntArray): Triple<IntArray, IntArray, IntArray> {
    val zeroSixNine = filter { it.size == 6 }
    val (nine, zeroSix) = zeroSixNine.partition { num -> four.isIn(num) }
    val (zero, six) = zeroSix.partition { num -> seven.isIn(num) }
    return Triple(zero.first(), six.first(), nine.first())
}

fun List<IntArray>.findTwoThreeFiveBy(one: IntArray, six: IntArray): Triple<IntArray, IntArray, IntArray> {
    val twoThreeFive = filter { it.size == 5 }
    val (three, twoFive) = twoThreeFive.partition { num -> one.isIn(num) }
    val (five, two) = twoFive.partition { num -> num.isIn(six) }
    return Triple(two.first(), three.first(), five.first())
}

fun solve(input: List<String>) {
    val displayed = input.asSequence().map { line ->
        val (left, right) = line.split("|").map { half ->
            half.split(" ").filter { it.isNotEmpty() }.map { s -> s.chars().toArray() }
        }
        left to right
    }
    val decodedNumsSum = displayed.sumOf { (left, right) ->
        val one = left.first { it.size == 2 }
        val seven = left.first { it.size == 3 }
        val four = left.first { it.size == 4 }
        val eight = left.first { it.size == 7 }
        val (zero, six, nine) = left.findZeroSixNineBy(four, seven)
        val (two, three, five) = left.findTwoThreeFiveBy(one, six)

        val number = right.fold("") { acc, c ->
            val num = when {
                zero.sameAs(c) -> 0
                one.sameAs(c) -> 1
                two.sameAs(c) -> 2
                three.sameAs(c) -> 3
                four.sameAs(c) -> 4
                five.sameAs(c) -> 5
                six.sameAs(c) -> 6
                seven.sameAs(c) -> 7
                eight.sameAs(c) -> 8
                nine.sameAs(c) -> 9
                else -> throw Exception("Unknown number encountered")
            }
            acc + num
        }
        number.toInt()
    }
    println(decodedNumsSum)
}

fun main() {
    runTests("08", ::solve)
}