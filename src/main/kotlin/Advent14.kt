
fun main() {
    val input = Utils.readInput(14)

    val disk = Array(128) { row ->
        Task10.knotHash("$input-$row")
            .asSequence()
            .map { it.toString().toInt(16) }
            .map { it.toString(2).padStart(4, '0') }
            .flatMap(String::asSequence)
            .map { it == '1' }
            .toList().toTypedArray()
    }

    // taskA
    val resultA = disk.sumBy { it.sumBy { v -> v.toInt() }}
    println("A: $resultA")

    // taskB
    var groups = 0
    val visited = Array(128) { Array(128) { false } }
    while (true) {
        val start = Utils.indexOfOrNull(disk) { v, i, j -> v && !visited[i][j] } ?: break
        groups++

        Dijkstra.findTargets(start, { _ -> true }, { p ->
                Direction.values().map { dir -> p.move(dir) }.filter { pp ->
                    pp.x in (0..127) && pp.y in (0..127) && disk[pp.x][pp.y] && !visited[pp.x][pp.y]
                }
            }).forEach { (p, _) -> visited[p.x][p.y] = true }
        visited[start.x][start.y] = true
    }
    println("B: $groups")

}




