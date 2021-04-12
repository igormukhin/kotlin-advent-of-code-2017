fun main() {
    val input = Utils.readInput(5).lines().map { it.toInt() }

    // task A
    run {
        val numbers = input.toMutableList()
        var steps = 0
        var offset = 0
        do {
            numbers[offset]++
            offset = offset + numbers[offset] - 1
            steps++
        } while (offset in 0 until numbers.size)
        println("A: $steps")
    }

    // task B
    run {
        val numbers = input.toMutableList()
        var steps = 0
        var offset = 0
        do {
            val distance = numbers[offset]
            if (distance >= 3) numbers[offset]-- else numbers[offset]++
            offset += distance
            steps++
        } while (offset in 0 until numbers.size)
        println("B: $steps")
    }
}
