fun main() {
    val input = mutableListOf<Pipe>()
    Utils.readInput(12).lines().forEach { parseLine(it, input) }

    // taskA
    val group0Size = findGroup(input, 0).size
    println("A: $group0Size")

    // taskB
    val pipes = input.toMutableList()
    var groups = 0
    while (pipes.isNotEmpty()) {
        val group = findGroup(pipes, pipes.first().from)
        pipes.removeAll { it.from in group || it.to in group }
        groups++
    }

    println("B: $groups")
}

private fun findGroup(pipes: List<Pipe>, startWith: Int): Set<Int> {
    val visited = mutableSetOf<Int>()
    visited.add(startWith)

    do {
        var updated = false
        pipes.forEach { pipe ->
            if (pipe.from in visited && pipe.to !in visited) { visited += pipe.to; updated = true }
            if (pipe.to in visited && pipe.from !in visited) { visited += pipe.from; updated = true }
        }
    } while (updated)

    return visited
}

private data class Pipe (val from: Int, val to: Int)

private val LINE_PATTERN = Regex("(\\d+) <-> (.+)").toPattern()

private fun parseLine(line: String, pipes: MutableList<Pipe>) {
    val matcher = LINE_PATTERN.matcher(line)
    require(matcher.matches())
    val from = matcher.group(1).toInt()
    val tos = matcher.group(2).split(", ").map { it.toInt() }
    tos.forEach { to -> pipes.add(Pipe(from, to)) }
}
