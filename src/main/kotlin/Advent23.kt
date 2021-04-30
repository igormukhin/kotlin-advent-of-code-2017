fun main() {
    val input = Utils.readInput(23)
    val lines = input.lines()

    var pos = 0
    val registers = mutableMapOf<String, Long>()
    ('a'..'z').map(Char::toString).forEach { registers[it] = 0 }

    fun valueOf(token: String): Long = if (token[0] in 'a'..'z') {
        registers[token]!!
    } else {
        token.toLong()
    }

    var muls = 0
    while (pos < lines.size) {
        val line = lines[pos]
        val tokens = line.split(" ")
        when (tokens[0]) {
            "set" -> registers[tokens[1]] = valueOf(tokens[2])
            "sub" -> registers[tokens[1]] = registers[tokens[1]]!! - valueOf(tokens[2])
            "mul" -> { registers[tokens[1]] = registers[tokens[1]]!! * valueOf(tokens[2]); muls++ }
            "jnz" -> if (valueOf(tokens[1]) != 0L) pos = pos + valueOf(tokens[2]).toInt() - 1
        }
        pos++
    }
    println("A: $muls")

    run {
        val bStart = 106500L
        var num = bStart
        var hits = 0L

        while (num <= bStart + 17000) {
            var divisor = 2L
            do {
                if (num % divisor == 0L && num / divisor >= 2) {
                    hits++
                    break
                }

                divisor++
            } while (divisor <= num)

            num += 17
        }

        println("B: $hits")
    }

}
