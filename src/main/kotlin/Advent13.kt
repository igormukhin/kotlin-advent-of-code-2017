
fun main() {
    val input = Utils.readInput(13).lines().associate {
        val (v1, v2) = it.split(": ").map(String::toInt)
        v1 to v2
    }

    fun calcSeverity(start: Int, block: (Map.Entry<Int, Int>) -> Int): Int {
        return input.asSequence()
            .filter { (d, r) -> (start + d) % (r * 2 - 2) == 0 }
            .sumBy(block)
    }

    // task A
    val severity = calcSeverity(0) { (d, r) -> d * r }
    println("A: $severity")

    // task B
    var delay = -1
    do {
        val problems = calcSeverity(++delay) { (_, _) -> 1 }
    } while (problems != 0)
    println("B: $delay")
}




