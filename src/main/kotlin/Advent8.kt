data class Operation(val register: String, val doOper: String, val byValue: Int,
                     val ifRegister: String, val ifOper: String, val ifValue: Int) {
    companion object {
        val PATTERN = Regex("([a-z]+) (inc|dec) (-?[0-9]+) if ([a-z]+) ([=<>!]+) (-?[0-9]+)").toPattern()

        fun parse(line: String) : Operation {
            val matcher = PATTERN.matcher(line)
            if (!matcher.matches()) throw Exception("Bad line: $line")
            return Operation(matcher.group(1), matcher.group(2), matcher.group(3).toInt(),
                    matcher.group(4), matcher.group(5), matcher.group(6).toInt())
        }
    }
}

fun main(args: Array<String>) {
    val operations = Utils.readInput("Advent8").lines().map { Operation.parse(it) }
    val registers = mutableMapOf<String, Int>()
    operations.forEach { operation ->
        val checkVal = registers.getOrDefault(operation.ifRegister, 0)
        val ifResult = when (operation.ifOper) {
            "==" -> checkVal == operation.ifValue
            "!=" -> checkVal != operation.ifValue
            ">" -> checkVal > operation.ifValue
            "<" -> checkVal < operation.ifValue
            ">=" -> checkVal >= operation.ifValue
            "<=" -> checkVal <= operation.ifValue
            else -> throw Exception()
        }
        if (ifResult) {
            val beforeVal = registers.getOrDefault(operation.register, 0)
            when (operation.doOper) {
                "inc" -> registers[operation.register] = beforeVal + operation.byValue
                "dec" -> registers[operation.register] = beforeVal - operation.byValue
            }
        }
    }

    println(registers.values.max())
}
