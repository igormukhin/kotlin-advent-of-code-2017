import kotlin.math.max

fun main() {
    val input = Utils.readInput(24)
    val components = input.lines().map { ln ->
        val (p1, p2) = ln.split("/").map(String::toInt)
        Component(p1, p2)
    }

    run {
        var maxStrength = 0
        var longestStrength = Pair(0, 0)

        val bridge = Array(components.size) { -1 }
        val used = Array(components.size) { false }
        val endPins = Array(components.size) { -1 }
        var length = 1

        fun strength(): Int =
            (0 until length).asSequence().map { components[bridge[it]] }.map { it.pins1 + it.pins2 }.sum()

        fun go() {
            for (i in components.indices) {
                if (used[i]) continue
                var ok = false
                if (length == 1) {
                    if (components[i].pins1 == 0) {
                        endPins[length - 1] = components[i].pins2
                        ok = true
                    } else if (components[i].pins2 == 0) {
                        endPins[length - 1] = components[i].pins1
                        ok = true
                    }
                } else {
                    if (endPins[length - 1 - 1] == components[i].pins1) {
                        endPins[length - 1] = components[i].pins2
                        ok = true
                    } else if (endPins[length - 1 - 1] == components[i].pins2) {
                        endPins[length - 1] = components[i].pins1
                        ok = true
                    }
                }

                if (ok) {
                    used[i] = true
                    bridge[length - 1] = i
                    val curStrength = strength()
                    maxStrength = max(maxStrength, curStrength)
                    longestStrength = maxLongestStrength(longestStrength, Pair(length, curStrength))
                    length++
                    if (length <= components.size) {
                        go()
                    }
                    length--
                    used[i] = false
                }
            }
        }

        go()

        println("A: $maxStrength")
        println("B: ${longestStrength.second}")
    }
}

private fun maxLongestStrength(br1: Pair<Int, Int>, br2: Pair<Int, Int>): Pair<Int, Int> {
    return when {
        br2.first > br1.first -> br2
        br1.first > br2.first -> br1
        br2.second > br1.second -> br2
        else -> br1
    }
}

private data class Component(val pins1: Int, val pins2: Int)