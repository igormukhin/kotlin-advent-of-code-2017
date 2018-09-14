import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    val input = Utils.readInput("Advent3").toInt()
    println(distanceFor(input))
}

fun distanceFor(address: Int): Int {
    if (address <= 1) return 0

    var ring = 0
    var rest = address - 1
    while (true) {
        ring++
        val ringSize = (ring * 2) * 4
        if (rest <= ringSize) break
        rest -= ringSize
    }

    rest %= (ring * 2)
    val shift = (ring - rest).absoluteValue

    return ring + shift
}
