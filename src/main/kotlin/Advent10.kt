fun main(args: Array<String>) {
    val lengths = Utils.readInput("Advent10").split(',').map { it.toInt() }
    val listSize = 256

    var pos = 0
    var skipSize = 0

    val list = mutableListOf<Int>()
    (0 until listSize).forEach { list += it }

    lengths.forEach { length ->
        list.reverseWrapping(pos, length)

        pos += length + skipSize
        skipSize++
    }

    println(list[0] * list[1])
}

private fun <E> MutableList<E>.reverseWrapping(pos: Int, length: Int) {
    var startIdx = pos
    var endIdx = pos + length - 1

    while (startIdx < endIdx) {
        swapW(startIdx, endIdx)
        startIdx++
        endIdx--
    }
}

private fun <E> MutableList<E>.swapW(pos1: Int, pos2: Int) {
    if (pos1 == pos2) return
    val tmp = getW(pos1)
    setW(pos1, getW(pos2))
    setW(pos2, tmp)
}

private fun <E> MutableList<E>.dewrap(idx: Int): Int {
    val rem = idx % size
    return if (rem < 0) size - 1 - rem else rem
}

private fun <E> MutableList<E>.getW(idx: Int) = get(dewrap(idx))
private fun <E> MutableList<E>.setW(idx: Int, value: E) = set(dewrap(idx), value)
