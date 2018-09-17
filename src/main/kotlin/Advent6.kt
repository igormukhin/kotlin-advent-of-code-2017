fun main(args: Array<String>) {
    val banks = Utils.readInput("Advent6").split(Regex("\\s+"))
            .asSequence().map { it.toInt() }.toMutableList()
    var cycles = 0
    val seen = mutableSetOf<List<Int>>()

    while (true) {
        seen.add(banks.toList())
        cycles++
        val max = banks.max() ?: 0
        var idx = banks.indexOf(max)
        var value = banks[idx]
        banks[idx] = 0
        while (value > 0) {
            idx = if (idx < banks.size - 1) idx + 1 else 0
            banks[idx]++
            value--
        }

        if (seen.contains(banks)) break
    }
    println(cycles)
}
