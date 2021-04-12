fun main() {
    val banks = Utils.readInput(6).split(Regex("\\s+"))
            .asSequence().map { it.toInt() }.toMutableList()
    var cycles = 0
    val seen = mutableMapOf<List<Int>, Int>()

    do {
        seen[banks.toList()] = cycles
        cycles++
        val max = banks.maxOrNull() ?: 0
        var idx = banks.indexOf(max)
        var value = banks[idx]
        banks[idx] = 0
        while (value > 0) {
            idx = if (idx < banks.size - 1) idx + 1 else 0
            banks[idx]++
            value--
        }

    } while (banks !in seen)

    println("A: $cycles")
    println("B: ${cycles - seen[banks]!!}")
}
