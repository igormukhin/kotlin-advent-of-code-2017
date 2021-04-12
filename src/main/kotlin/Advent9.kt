import StreamParserState.*

fun main() {
    val input = Utils.readInput(9)
    var score = 0
    var depth = 0
    var state = IN_GROUP
    var garbage = 0

    input.forEach { ch ->
        when (state) {
            IN_GROUP -> {
                when (ch) {
                    '{' -> {
                        depth++
                        score += depth
                    }
                    '}' -> {
                        depth--
                    }
                    '<' -> {
                        state = IN_GARBAGE
                    }
                }
            }
            IN_GARBAGE -> {
                when (ch) {
                    '>' -> state = IN_GROUP
                    '!' -> state = IGNORE_NEXT
                    else -> garbage++
                }
            }
            IGNORE_NEXT -> {
                state = IN_GARBAGE
            }
        }
    }

    println("A: $score")
    println("B: $garbage")
}

private enum class StreamParserState {
    IN_GROUP,
    IN_GARBAGE,
    IGNORE_NEXT
}
