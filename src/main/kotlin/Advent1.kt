fun CharSequence.charAtLooping(i : Int) = this[i % length]

fun main(args: Array<String>) {
    val input = Utils.readInput("Advent1")

    var sum = 0
    0.until(input.length).forEach {
        sum += if (input[it] == input.charAtLooping(it + 1)) input[it].toInt() else 0
    }

    println(sum)
}