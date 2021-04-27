import kotlin.math.abs

fun main() {
    val input = Utils.readInput(22)
    val startingMap = mutableMapOf<Point, Int>()
    input.lines().forEachIndexed { i, line ->
        line.forEachIndexed { j, ch ->
            startingMap[Point(i, j)] = if (ch == '#') 1 else 0
        }
    }

    // part A
    run {
        val map = startingMap.toMutableMap()
        var pos = Point(map.maxOf { it.key.x } / 2, map.maxOf { it.key.y } / 2)
        var dir = Direction.UP
        val steps = 10000
        var infections = 0
        repeat(steps) {
            val cur = map[pos] ?: 0
            dir = (if (cur == 1) dir.turnRight() else dir.turnLeft())
            if (cur == 0) infections++
            map[pos] = abs(cur - 1)
            pos = pos.move(dir)
        }

        println("A: $infections")
    }

    // part B
    run {
        val map = startingMap.mapValues { e -> if (e.value == 1) 2 else 0 }.toMutableMap()
        var pos = Point(map.maxOf { it.key.x } / 2, map.maxOf { it.key.y } / 2)
        var dir = Direction.UP
        val steps = 10_000_000
        var infections = 0
        repeat(steps) {
            val cur = map[pos] ?: 0
            dir = when(cur) {
                0 -> dir.turnLeft()
                2 -> dir.turnRight()
                3 -> dir.turnAround()
                else -> dir
            }
            if (cur == 1) infections++
            map[pos] = (cur + 1) % 4
            pos = pos.move(dir)
        }

        println("B: $infections")
    }
}
