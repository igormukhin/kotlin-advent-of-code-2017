fun main() {
    val input = Utils.readInput(2)
    val linesOfNums = input.lines().map { line -> line.split(Regex("\\s+")).map { it.toInt() } }

    // a
    val checksumA  = linesOfNums.sumOf {
        (it.maxOrNull() ?: 0) - (it.minOrNull() ?: 0)
    }
    println("A: $checksumA")

    // b
    val checksumB  = linesOfNums.sumOf { findDivisible(it) }
    println("B: $checksumB")
}

private fun findDivisible(row: List<Int>): Int {
    (1 until row.size).forEach { i1 ->
        (0 until i1).forEach { i2 ->
            if (row[i1] % row[i2] == 0)
                return row[i1] / row[i2]
            if (row[i2] % row[i1] == 0)
                return row[i2] / row[i1]
        }
    }
    throw RuntimeException("Illegal row $row")
}