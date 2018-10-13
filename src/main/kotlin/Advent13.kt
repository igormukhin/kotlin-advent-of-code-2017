
fun main(args: Array<String>) {
    val input: Map<Int, Int> = Utils.readInput("Advent13").lines().map {
        val parts = it.split(": ")
        parts[0].toInt() to parts[1].toInt()
    }.toMap()

    var severity = 0
    
    input.forEach { depth, range ->
        if (depth % (range * 2 - 2) == 0) {
            severity += depth * range
        }
    }

    println(severity)
}




