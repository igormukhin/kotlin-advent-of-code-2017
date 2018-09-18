import StreamParserState.*

enum class StreamParserState {
    IN_GROUP,
    IN_GARBAGE,
    IGNORE_NEXT
}

fun main(args: Array<String>) {
    val stream = Utils.readInput("Advent9")
    var score = 0
    var depth = 0
    var state = IN_GROUP

    stream.asSequence().forEach { ch ->
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
                }
            }
            IGNORE_NEXT -> {
                state = IN_GARBAGE
            }
        }
    }

    println(score)
}
