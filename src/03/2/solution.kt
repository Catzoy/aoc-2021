package `03`.`2`

import java.io.File

fun List<String>.countZeroesAndOnesAt(index: Int): Pair<Int, Int> =
    fold(0 to 0) { (zeroes, ones), bin ->
        val c = bin[index]
        (zeroes + if (c == '0') 1 else 0) to (ones + if (c == '1') 1 else 0)
    }

fun List<String>.filterByBinRatioAt(index: Int, bitValueComparator: (zeroes: Int, ones: Int) -> Char): List<String> {
    val (zeroes, ones) = countZeroesAndOnesAt(index)
    val requestedBitValue = bitValueComparator(zeroes, ones)
    return this.takeIf { it.size > 1 }?.filter { it[index] == requestedBitValue } ?: this
}

fun main() {
    val input = File("src/03", "input_full.txt").readLines()
    val inputLen = input[0].length
    val (oxygens, co2s) = (0 until inputLen).fold(input to input) { (oxygen, co2), index ->
        oxygen.filterByBinRatioAt(index) { zeroes, ones -> if (zeroes > ones) '0' else '1' } to
                co2.filterByBinRatioAt(index) { zeroes, ones -> if (zeroes > ones) '1' else '0' }
    }
    assert(oxygens.size == 1)
    assert(co2s.size == 1)
    println("${oxygens[0]} | ${co2s[0]}")
    val oxygen = oxygens[0].toInt(radix = 2)
    val co2 = co2s[0].toInt(radix = 2)
    println("$oxygen | $co2 | ${oxygen * co2}")
}