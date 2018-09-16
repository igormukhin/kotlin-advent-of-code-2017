fun main(args: Array<String>) {
    val input = Utils.readInput("Advent4").lines()
    val validCount = input.asSequence().filter { isValid(it) }.count()
    println(validCount)
}

fun isValid(line: String): Boolean {
    val words = line.split(Regex("\\s+"))
    val uniqueWords = words.toSet()
    return words.size == uniqueWords.size
}
