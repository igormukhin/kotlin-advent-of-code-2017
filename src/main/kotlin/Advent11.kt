import kotlin.math.abs
import kotlin.math.max

fun main() {
    val moves = Utils.readInput(11).split(',')

    var x = 0
    var y = 0
    var max = Int.MIN_VALUE

    moves.forEach { move ->
        when (move) {
            "n" -> y += 2
            "s" -> y -= 2
            "ne" -> { y++; x += 2 }
            "se" -> { y--; x += 2 }
            "nw" -> { y++; x -= 2 }
            "sw" -> { y--; x -= 2 }
            else -> throw RuntimeException("No!")
        }
        max = max(max, stepsAway(x, y))
    }

    val resultA = stepsAway(x, y)
    println("A: $resultA")
    println("B: $max")
}

private fun stepsAway(x: Int, y: Int): Int {
    val ax = abs(x)
    val ay = abs(y)
    return if (ax / 2 >= ay) ax / 2 else (ax / 2) + ((ay - (ax / 2)) / 2)
}
