fun main() {
    val input = Utils.readInput(10)

    // task A
    run {
        val lengths = input.split(',').map { it.toInt() }
        var pos = 0
        var skipSize = 0
        val array = IntArray(256) { it }

        lengths.forEach { length ->
            array.reverseWrapping(pos, length)

            pos += length + skipSize
            skipSize++
        }

        val resultA = array[0] * array[1]
        println("A: $resultA")
    }

    // task B
    run {
        val data = input
        val resultB = Task10.knotHash(data)
        println("B: $resultB")
    }
}

object Task10 {
    fun knotHash(data: String): String {
        val lengths = data.asSequence().map(Char::toInt).toMutableList()
        lengths.addAll(listOf(17, 31, 73, 47, 23))
        var pos = 0
        var skipSize = 0
        val array = IntArray(256) { it }

        repeat(64) {
            lengths.forEach { length ->
                array.reverseWrapping(pos, length)

                pos += length + skipSize
                skipSize++
            }
        }

        return array.toList().chunked(16)
            .map { lst -> lst.reduce { acc, n -> acc.xor(n) } }
            .joinToString(separator = "") { it.toString(16).padStart(2, '0') }
    }
}

private fun IntArray.reverseWrapping(pos: Int, length: Int) {
    var startIdx = pos
    var endIdx = pos + length - 1

    while (startIdx < endIdx) {
        swapW(startIdx, endIdx)
        startIdx++
        endIdx--
    }
}

private fun IntArray.swapW(pos1: Int, pos2: Int) {
    if (pos1 == pos2) return
    val tmp = getW(pos1)
    setW(pos1, getW(pos2))
    setW(pos2, tmp)
}

private fun IntArray.dewrap(idx: Int): Int {
    val rem = idx % size
    return if (rem < 0) size - 1 - rem else rem
}

private fun IntArray.getW(idx: Int) = this[dewrap(idx)]
private fun IntArray.setW(idx: Int, value: Int) { this[dewrap(idx)] = value }
