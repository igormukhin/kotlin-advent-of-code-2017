import Direction.*
import kotlin.math.absoluteValue

fun main() {
    val input = Utils.readInput(3).toInt()

    val distance = taskA(input)
    println("A: $distance")

    val nextLarger = taskB(input)
    println("B: $nextLarger")
}

private fun taskA(address: Int): Int {
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

private fun taskB(address: Int): Int {
    val map = mutableMapOf(Point(0, 0) to 1, Point(0, 1) to 1)
    var dir = UP
    var current = Point(0, 1)

    do {
        current = current.move(dir)
        map[current] =
                (map[current.move(UP)] ?: 0) +
                (map[current.move(LEFT)] ?: 0) +
                (map[current.move(RIGHT)] ?: 0) +
                (map[current.move(DOWN)] ?: 0) +
                (map[current.move(UP).move(RIGHT)] ?: 0) +
                (map[current.move(UP).move(LEFT)] ?: 0) +
                (map[current.move(DOWN).move(RIGHT)] ?: 0) +
                (map[current.move(DOWN).move(LEFT)] ?: 0)
        if (current.move(dir.turnLeft()) !in map) {
            dir = dir.turnLeft()
        }
    } while (map[current]!! <= address)

    return map[current]!!
}
