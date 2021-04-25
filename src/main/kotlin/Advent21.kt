import java.lang.IllegalStateException

fun main() {
    val input = Utils.readInput(21)

    val starting = Image.parse(".#./..#/###")
    val rules = input.lines().associate(::parseRule).toMutableMap()
    rules.entries.toList().forEach { (from, to) ->
        from.forEachTransformation { if (rules[it] == null) rules[it] = to }
    }

    println("A: ${runTask(5, starting, rules)}")
    println("B: ${runTask(18, starting, rules)}")
}

private fun runTask(
    iterations: Int,
    starting: Image,
    rules: Map<Image, Image>
): Int {
    val result = (0 until iterations).fold(starting) { image, iter ->
        val div = when {
            image.size % 2 == 0 -> 2
            image.size % 3 == 0 -> 3
            else -> throw IllegalStateException("${image.size}")
        }

        val dim = image.size / div
        val newDiv = if (div == 2) 3 else 4
        val newImage = Image.withSize(newDiv * dim)
        for (i in 0 until dim) {
            for (j in 0 until dim) {
                val sub = image.subImage(i * div, j * div, div)
                val translates = rules[sub]!!
                newImage.put(i * newDiv, j * newDiv, translates)
            }
        }

        newImage
    }

    return result.trues()
}

private fun parseRule(str: String): Pair<Image, Image> {
    val (from, to) = str.split(" => ")
    return Pair(Image.parse(from), Image.parse(to))
}

private class Image(val data: Array<Array<Boolean>>, val top: Int = 0, val left: Int = 0,
                    val size: Int = data.size,
                    val transformer: (Pair<Int, Int>) -> Pair<Int, Int> = { p -> p }) {
    val dataSize = data.size

    init {
        require(data.all { it.size == dataSize })
        require(size in 1..dataSize)
        require(top in 0..dataSize)
        require(top + size <= dataSize)
        require(left in 0..dataSize)
        require(left + size <= dataSize)
    }

    fun transform(transformer: (Pair<Int, Int>) -> Pair<Int, Int>): Image {
        return Image(data, top, left, size) { p -> this.transformer(transformer(p)) }
    }

    companion object {
        fun withSize(size: Int): Image {
            return Image(Array(size) { Array(size) { false } })
        }

        fun parse(str: String): Image {
            val lines = str.split("/")
            val data = Array(lines.size) { i -> Array(lines.size) { j -> lines[i][j] == '#' } }
            return Image(data)
        }
    }

    fun subImage(top: Int, left: Int, size: Int): Image {
        return Image(data, this.top + top, this.left + left, size)
    }

    fun at(pos: Pair<Int, Int>): Boolean {
        require(pos.first in 0 until size)
        require(pos.second in 0 until size)
        val pos2 = transformer(pos)
        require(pos2.first in 0 until size)
        require(pos2.second in 0 until size)
        return data[top + pos2.first][left + pos2.second]
    }

    fun contentEquals(image: Image): Boolean {
        if (size != image.size) return false
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (at(Pair(i, j)) != image.at(Pair(i, j))) {
                    return false
                }
            }
        }
        return true
    }

    fun put(top: Int, left: Int, image: Image) {
        for (i in 0 until image.size) {
            for (j in 0 until image.size) {
                data[top + i][left + j] = image.at(Pair(i, j))
            }
        }
    }

    fun materialize(): Image {
        val newData = Array(size) { i -> Array(size) { j -> at(Pair(i, j)) } }
        return Image(newData)
    }

    fun forEachRotation(consumer: (Image) -> Unit) {
        var src = this.materialize()
        consumer(src)
        src = transform { rotatePos(it) }.materialize()
        consumer(src)
        src = src.transform { rotatePos(it) }.materialize()
        consumer(src)
        src = src.transform { rotatePos(it) }.materialize()
        consumer(src)
    }

    fun forEachTransformation(consumer: (Image) -> Unit) {
        forEachRotation(consumer)
        transform { flipPosH(it) }.forEachRotation(consumer)
        transform { flipPosW(it) }.forEachRotation(consumer)
        transform { flipPosW(flipPosH(it)) }.forEachRotation(consumer)
    }

    private fun rotatePos(pos: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(size - pos.second - 1, pos.first)
    }

    private fun flipPosH(pos: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(pos.first, size - pos.second - 1)
    }

    private fun flipPosW(pos: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(size - pos.first - 1, pos.second)
    }

    fun trues(): Int {
        var cnt = 0
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (at(Pair(i, j))) cnt++
            }
        }
        return cnt
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        return contentEquals(other)
    }

    override fun hashCode(): Int {
        var result = materialize().data.contentDeepHashCode()
        result = 31 * result + size
        return result
    }

    fun print() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                print(if (at(Pair(i, j))) '#' else '.')
            }
            println()
        }
    }
}
