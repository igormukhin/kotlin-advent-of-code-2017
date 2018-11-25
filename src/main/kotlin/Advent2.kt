fun main(args: Array<String>) {
    val lines: List<List<Int>> = Utils.readInput("Advent2").lines()
            .map { line ->
                line.split(Regex("\\s+")).map { it.toInt() }
            }

    // a
    val checksum  = lines.map {
        (it.max() ?: 0) - (it.min() ?: 0)
    }.sum()
    println(checksum)

    // b
    val checksumB  = lines.map { row ->
        getEvenlyDivisible(row)
    }.sum()
    println(checksumB)
}

private fun getEvenlyDivisible(row: List<Int>): Int {
    1.until(row.size).forEach { i1 ->
        0.until(i1).forEach { i2 ->
            if (row[i1] % row[i2] == 0)
                return row[i1] / row[i2]
            if (row[i2] % row[i1] == 0)
                return row[i2] / row[i1]
        }
    }
    throw RuntimeException("Illegal row $row")
}