import Chain.Link

fun main() {
    val input = Utils.readInput(17)
    val steps = input.toInt()
    val times = 2017

    // taskA
    run {
        var link = Link(0)
        var pos = 0
        repeat (times) { i ->
            pos = 1 + (pos + steps) % (i + 1)
            link = link.leftmost().goRight(pos - 1)
            link = Link(i + 1).apply { insertAfter(link) }
        }
        println("A: ${link.next?.value}")
    }

    // taskB
    run {
        var zeroPos = 0
        var valueAfterZero = 0
        var pos = 0
        repeat (50_000_000) { i ->
            pos = 1 + (pos + steps) % (i + 1)
            if (pos > zeroPos) {
                if (pos == zeroPos + 1) valueAfterZero = i + 1
            } else {
                zeroPos++
            }
        }
        println("B: $valueAfterZero")
    }
}



