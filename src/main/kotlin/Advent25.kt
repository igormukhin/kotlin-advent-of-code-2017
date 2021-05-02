fun main() {
    val input = Utils.readInput(25).lines()

    var state = "A"
    var diagSteps = 0
    val rules = mutableMapOf<Pair<String, Int>, Triple<Int, Int, String>>()
    val ones = mutableSetOf<Int>()

    var i = 0
    while (i < input.size) {
        val ln = input[i]
        when {
            ln.startsWith("Begin in state") -> state = "A"
            ln.startsWith("Perform a diag") -> diagSteps = ln.tailToken(" ").toInt()
            ln.startsWith("In state") -> {
                val st = ln.tailToken(":")
                rules[Pair(st, 0)] = Triple(input[i + 2].tailToken(".").toInt(),
                    if (input[i + 3].tailToken(".") == "right") 1 else -1,
                    input[i + 4].tailToken("."))
                rules[Pair(st, 1)] = Triple(input[i + 6].tailToken(".").toInt(),
                    if (input[i + 7].tailToken(".") == "right") 1 else -1,
                    input[i + 8].tailToken("."))
                i += 8
            }
        }
        i++
    }

    var pos = 0
    repeat(diagSteps) {
        val rule = rules[Pair(state, if (pos in ones) 1 else 0)]!!
        if (rule.first == 1) ones.add(pos) else ones.remove(pos)
        pos += rule.second
        state = rule.third
    }

    println("A: ${ones.size}")
}

private fun String.tailToken(beforeLast: String): String {
    return this.substringBeforeLast(beforeLast).substringAfterLast(" ")
}
