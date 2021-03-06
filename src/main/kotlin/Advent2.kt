fun main(args: Array<String>) {
    val lines: List<List<Int>> = Utils.readInput("Advent2").lines()
            .map { line ->
                line.split(Regex("\\s+")).map { it.toInt() }
            }

    val checksum  = lines.map {
        (it.max() ?: 0) - (it.min() ?: 0)
    }.sum()

    println(checksum)
}