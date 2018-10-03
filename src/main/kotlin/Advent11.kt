fun main(args: Array<String>) {
    val moves = Utils.readInput("Advent11").split(',')

    var x = 0
    var y = 0

    moves.forEach { move ->
        when (move) {
            "n" -> y += 2
            "s" -> y -= 2
            "ne" -> { y++; x += 2 }
            "se" -> { y--; x += 2 }
            "nw" -> { y++; x -= 2 }
            "sw" -> { y--; x -= 2 }
            else -> throw Exception("No!")
        }
    }

    x = Math.abs(x)
    y = Math.abs(y)

    val stepsAway = if (x / 2 >= y) x / 2 else (x / 2) + ((y - (x / 2)) / 2)

    println(stepsAway)
}
