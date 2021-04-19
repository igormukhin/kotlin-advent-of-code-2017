
fun main() {
    val input = Utils.readInput(18)
    val lines = input.lines()

    taskA(lines)
    taskB(lines)
}

private fun taskA(lines: List<String>) {
    var pos = 0L
    val registers = mutableMapOf<String, Long>()
    ('a'..'z').map(Char::toString).forEach { registers[it] = 0 }

    fun valueOf(token: String): Long = if (token[0] in 'a'..'z') {
        registers[token]!!
    } else {
        token.toLong()
    }

    var played = 0L
    var firstRecover = 0L
    while (pos < lines.size) {
        val line = lines[pos.toInt()]
        val tokens = line.split(" ")
        when (tokens[0]) {
            "snd" -> played = registers[tokens[1]]!!
            "rcv" -> if (registers[tokens[1]]!! != 0L) if (firstRecover == 0L) {
                firstRecover = played
                break
            }
            "set" -> registers[tokens[1]] = valueOf(tokens[2])
            "add" -> registers[tokens[1]] = registers[tokens[1]]!! + valueOf(tokens[2])
            "mul" -> registers[tokens[1]] = registers[tokens[1]]!! * valueOf(tokens[2])
            "mod" -> registers[tokens[1]] = registers[tokens[1]]!! % valueOf(tokens[2])
            "jgz" -> if (registers[tokens[1]]!! > 0) pos = pos + valueOf(tokens[2]) - 1L
        }
        pos++
    }
    println("A: $firstRecover")
}

private fun taskB(program: List<String>) {
    val executions = Array(2) { i -> Execution(program).apply { registers["p"] = i.toLong() } }
    do {
        executions.forEachIndexed { i, execution ->
            check(execution.state != Execution.State.FINISHED)
            if (execution.state == Execution.State.WAIT_START) {
                execution.run(null)
            } else {
                val other = executions[(i + 1) % 2]
                if (other.sendQueue.isNotEmpty()) {
                    val rcv = other.sendQueue.removeFirst()
                    execution.run(rcv)
                }
            }
        }
    } while (!executions.all { it.state == Execution.State.WAIT_RECEIVE && it.sendQueue.isEmpty() })

    println("B: ${executions[1].sendCounter}")
}

private class Execution(val program: List<String>) {
    private var pos = 0L
    var registers = mutableMapOf<String, Long>()
    var state = State.WAIT_START
    val sendQueue = mutableListOf<Long>()
    var sendCounter = 0

    enum class State { WAIT_START, WAIT_RECEIVE, FINISHED }

    init {
        ('a'..'z').map(Char::toString).forEach { registers[it] = 0 }
    }

    fun run(rcv: Long?) {
        var rcvConsumed = (rcv == null)

        fun valueOf(token: String): Long = if (token[0] in 'a'..'z') {
            registers[token]!!
        } else {
            token.toLong()
        }

        while (pos < program.size) {
            val line = program[pos.toInt()]
            val tokens = line.split(" ")
            when (tokens[0]) {
                "snd" -> {
                    sendQueue.add(valueOf(tokens[1]))
                    sendCounter++
                }
                "rcv" -> {
                    if (rcvConsumed) {
                        state = State.WAIT_RECEIVE
                        break
                    } else {
                        registers[tokens[1]] = rcv!!
                        rcvConsumed = true
                    }
                }
                "set" -> registers[tokens[1]] = valueOf(tokens[2])
                "add" -> registers[tokens[1]] = registers[tokens[1]]!! + valueOf(tokens[2])
                "mul" -> registers[tokens[1]] = registers[tokens[1]]!! * valueOf(tokens[2])
                "mod" -> registers[tokens[1]] = registers[tokens[1]]!! % valueOf(tokens[2])
                "jgz" -> {
                    if (valueOf(tokens[1]) > 0)
                        pos = pos + valueOf(tokens[2]) - 1L
                }
            }
            pos++
        }
        if (pos > program.size) {
            state = State.FINISHED
        }
    }
}
