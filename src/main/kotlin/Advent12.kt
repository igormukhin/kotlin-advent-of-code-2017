private data class Pipe (val from: Int, val to: Int)
private fun Pipe.reversed(): Pipe = Pipe(to, from)

private val LINE_PATTERN = Regex("(\\d+) <-> (.+)").toPattern()

private fun parseLine(line: String, pipes: MutableList<Pipe>) {
    val matcher = LINE_PATTERN.matcher(line)
    if (!matcher.matches()) throw Exception("Bad line: $line")
    val from = matcher.group(1).toInt()
    val tos = matcher.group(2).split(", ").map { it.toInt() }
    tos.forEach { to -> pipes.add(Pipe(from, to)) }
}

fun main(args: Array<String>) {
    val pipes = mutableListOf<Pipe>()
    Utils.readInput("Advent12").lines().forEach { parseLine(it, pipes) }

    val visited = mutableSetOf<Int>()
    visited.add(0)

    while (true) {
        val visitedSize = visited.size
        pipes.forEach { pipe ->
            goFromTo(pipe, visited)
            goFromTo(pipe.reversed(), visited)
        }

        if (visited.size == visitedSize) break
    }

    println(visited.size)
}

private fun goFromTo(pipe: Pipe, visited: MutableSet<Int>) {
    if (!visited.contains(pipe.from)) return
    visited.add(pipe.to)
}



