
fun main() {
    val input = Utils.readInput(19).lines()
    val width = input.asSequence().map(String::length).maxOrNull()!!
    val map = Array(input.size) { i -> Array(width) { j -> if (j < input[i].length) input[i][j] else ' ' } }

    var pos = Point(0, map[0].indexOf('|'))
    var dir = Direction.DOWN
    val letters = StringBuilder()
    var steps = 0

    fun at(p: Point): Char = if (p.x in map.indices && p.y in 0 until width) map[p.x][p.y] else ' '
    fun turn(dir: Direction): Boolean {
        val ch = if (dir == Direction.DOWN || dir == Direction.UP) '|' else '-'
        return at(pos.move(dir)) == ch
    }

    do {
        steps++
        val ch = at(pos)
        pos = if (ch == '+') {
            if (turn(dir.turnRight())) {
                dir = dir.turnRight()
                pos.move(dir)
            } else {
                dir = dir.turnLeft()
                check(turn(dir))
                pos.move(dir)
            }
        } else {
            if (at(pos.move(dir)) == ' ') break
            pos.move(dir)
        }

        if (map[pos.x][pos.y] in 'A'..'Z') {
            letters.append(map[pos.x][pos.y])
        }
    } while (true)

    println("A: $letters")
    println("B: $steps")
}
