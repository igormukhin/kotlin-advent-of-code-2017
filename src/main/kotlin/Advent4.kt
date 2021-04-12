fun main() {
    val input = Utils.readInput(4).lines()

    val validCountA = input.asSequence().filter { isValidA(it) }.count()
    println("A: $validCountA")

    val validCountB = input.asSequence().filter { isValidB(it) }.count()
    println("B: $validCountB")
}

private fun isValidA(line: String): Boolean {
    val words = line.split(Regex("\\s+"))
    val uniqueWords = words.toSet()
    return words.size == uniqueWords.size
}

private fun isValidB(line: String): Boolean {
    val words = line.split(Regex("\\s+")).map { word ->
        word.asSequence().sorted().joinToString(separator = "")
    }

    val uniqueWords = words.toSet()
    return words.size == uniqueWords.size
}
