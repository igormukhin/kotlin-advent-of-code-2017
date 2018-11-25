private fun CharSequence.charAtLooping(i : Int) = this[i % length]

fun main(args: Array<String>) {
    val input = Utils.readInput("Advent1")

    println(calcSum(input, 1))
    println(calcSum(input, input.length / 2))
}

private fun calcSum(input: String, shift: Int): Int {
    var sum = 0
    0.until(input.length).forEach {
        sum += if (input[it] == input.charAtLooping(it + shift)) input[it].toString().toInt() else 0
    }
    return sum
}