package `11`.`1`

import runTests

class Octopus(
    value: Int
) {
    private var latestFlashStep = -1
    var value: Int = value
        private set

    fun hasFlashed(step: Int): Boolean {
        return latestFlashStep >= step
    }

    fun increase(step: Int) {
        if (!hasFlashed(step)) {
            value += 1
        }
    }

    fun flash(step: Int) {
        latestFlashStep = step
        value = 0
    }
}

fun List<List<Octopus>>.increase(step: Int) {
    forEach { line -> line.forEach { octopus -> octopus.increase(step) } }
}

fun List<List<Octopus>>.flash(step: Int, j: Int, i: Int): Int {
    val octopus = getOrNull(i)?.getOrNull(j) ?: return 0
    if (octopus.hasFlashed(step) || octopus.value <= 9)
        return 0

    octopus.flash(step)

    val adjacentOctopusesIndices = listOf(
        j to i - 1,
        j to i + 1,
        j - 1 to i,
        j - 1 to i - 1,
        j - 1 to i + 1,
        j + 1 to i - 1,
        j + 1 to i + 1,
        j + 1 to i,
    )

    for ((oJ, oI) in adjacentOctopusesIndices) {
        getOrNull(oI)?.getOrNull(oJ)?.increase(step)
    }

    return 1 + adjacentOctopusesIndices.sumOf { (oJ, oI) ->
        flash(step, oJ, oI)
    }
}

fun solve(input: List<String>) {
    val octopuses = input.map { s -> s.toCharArray().map { Octopus(value = it.digitToInt()) } }
    var flashes = 0
    for (step in 0 until 100) {
        octopuses.increase(step)
        for ((i, line) in octopuses.withIndex()) {
            for ((j, _) in line.withIndex()) {
                flashes += octopuses.flash(step, j, i)
            }
        }
    }
    println(flashes)
}


fun main() {
    runTests("11", ::solve)
}