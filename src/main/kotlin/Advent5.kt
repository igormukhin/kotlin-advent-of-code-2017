fun main(args: Array<String>) {
    val numbers = Utils.readInput("Advent5").lines().map { it.toInt() }.toMutableList()
    var steps = 0
    var offset = 0
    while (true) {
        numbers[offset]++
        offset = offset + numbers[offset] - 1
        steps++
        if (offset < 0 || offset >= numbers.size) {
            break
        }
    }
    println(steps)
}
