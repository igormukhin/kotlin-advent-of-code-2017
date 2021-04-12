fun main() {
    val programs = Utils.readInput(7).lines().map { Program.parse(it) }

    val refs = programs.map { it.holds }.flatten().toSet()
    val root = programs.first { it.name !in refs }
    println("A: ${root.name}")

    root.calcTotalWeight(programs)
    var maxLevel = Int.MIN_VALUE
    var correctWeight = -1
    root.forEachWithLeaves(programs) { children, level ->
        val map = mutableMapOf<Int, Int>()
        children.forEach { map.merge(it.totalWeight, 1, Int::plus) }
        if (map.size > 1 && level > maxLevel) {
            maxLevel = level
            val erroneous = map.minByOrNull { (_, v) -> v }!!.key
            val common = map.maxByOrNull { (_, v) -> v }!!.key
            correctWeight = children.first { it.totalWeight == erroneous }.weight + common - erroneous
        }
    }
    println("B: $correctWeight")
}

private data class Program(val name: String, val weight: Int, val holds: List<String>) {
    var totalWeight = 0

    companion object {
        val PATTERN = Regex("([a-z]+) \\((\\d+)\\)( -> (.+))*").toPattern()

        fun parse(line: String) : Program {
            val matcher = PATTERN.matcher(line)
            assert(matcher.matches())
            val holds = matcher.group(4)?.split(", ") ?: emptyList()
            return Program(matcher.group(1), matcher.group(2).toInt(), holds)
        }
    }

    fun calcTotalWeight(programs: List<Program>): Int {
        if (totalWeight == 0) {
            val childSum = holds.map { programs.first { p -> p.name == it } }
                .sumOf { it.calcTotalWeight(programs) }
            totalWeight = weight + childSum
        }
        return totalWeight
    }

    fun forEachWithLeaves(programs: List<Program>, thisLevel: Int = 0, block: (children: List<Program>, level: Int) -> Unit) {
        if (holds.isEmpty()) return
        val children = holds.map { programs.first { p -> p.name == it } }
        block(children, thisLevel)
        children.forEach { it.forEachWithLeaves(programs, thisLevel + 1, block) }
    }
}

