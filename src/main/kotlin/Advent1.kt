fun main() {
    val input = Utils.readInput(1)

    println("A: ${calcSum(input, 1)}")
    println("A: ${calcSum(input, input.length / 2)}")
}

private fun calcSum(input: String, shift: Int): Int {
    var sum = 0
    input.forEachIndexed { i, ch ->
        if (input[i] == input[(i + shift) % input.length]) {
            sum += ch.toString().toInt()
        }
    }
    return sum
}