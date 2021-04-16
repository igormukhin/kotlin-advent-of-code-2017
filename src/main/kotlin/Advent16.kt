
fun main() {
    val input = Utils.readInput(16)
    val moves = input.split(",")

    // taskA
    val programs = Array(16) { 'a' + it }
    dance(moves, programs)
    val orderA = programs.joinToString(separator = "")
    println("A: $orderA")

    // taskB
    var rep = 0
    val reps = 999_999_999
    while (rep < reps) {
        dance(moves, programs)
        rep++

        if (!programs.asSequence().mapIndexed { i, v -> 'a' + i == v }.any { !it }) {
            rep = reps - ((reps - rep) % (rep + 1))
        }
    }

    val orderB = programs.joinToString(separator = "")
    println("B: $orderB")
}

private fun dance(moves: List<String>, programs: Array<Char>) {
    moves.forEach { move ->
        when {
            move.startsWith("s") -> {
                val n = move.substring(1).toInt()
                val tail = programs.copyOfRange(16 - n, 16)
                (15 - n downTo 0).forEach { i -> programs[i + n] = programs[i] }
                (0 until n).forEach { i -> programs[i] = tail[i] }
            }
            move.startsWith("x") -> {
                val (a, b) = move.substring(1).split("/").map(String::toInt)
                swap(programs, a, b)
            }
            move.startsWith("p") -> {
                val (x, y) = move.substring(1).split("/").map { it[0] }
                swap(programs, programs.indexOf(x), programs.indexOf(y))
            }
        }
    }
}

private fun swap(programs: Array<Char>, a: Int, b: Int) {
    val p = programs[a]
    programs[a] = programs[b]
    programs[b] = p
}




