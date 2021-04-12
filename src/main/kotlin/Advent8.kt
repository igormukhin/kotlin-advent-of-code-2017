import kotlin.math.max

fun main() {
    val operations = Utils.readInput(8).lines().map(Operation::parse)
    val registers = mutableMapOf<String, Int>()
    var maxValue = Int.MIN_VALUE
    operations.forEach { operation ->
        val checkVal = registers[operation.ifRegister] ?: 0
        val ifResult = when (operation.ifOper) {
            "==" -> checkVal == operation.ifValue
            "!=" -> checkVal != operation.ifValue
            ">" -> checkVal > operation.ifValue
            "<" -> checkVal < operation.ifValue
            ">=" -> checkVal >= operation.ifValue
            "<=" -> checkVal <= operation.ifValue
            else -> throw RuntimeException()
        }
        if (ifResult) {
            val beforeVal = registers[operation.register] ?: 0
            when (operation.doOper) {
                "inc" -> registers[operation.register] = beforeVal + operation.byValue
                "dec" -> registers[operation.register] = beforeVal - operation.byValue
            }
            maxValue = max(registers[operation.register]!!, maxValue)
        }
    }

    println("A: ${registers.values.maxOrNull()}")
    println("B: $maxValue")
}

private data class Operation(val register: String, val doOper: String, val byValue: Int,
                             val ifRegister: String, val ifOper: String, val ifValue: Int) {
    companion object {
        val PATTERN = Regex("([a-z]+) (inc|dec) (-?[0-9]+) if ([a-z]+) ([=<>!]+) (-?[0-9]+)").toPattern()

        fun parse(line: String) : Operation {
            val matcher = PATTERN.matcher(line)
            assert(matcher.matches())
            return Operation(matcher.group(1), matcher.group(2), matcher.group(3).toInt(),
                matcher.group(4), matcher.group(5), matcher.group(6).toInt())
        }
    }
}
