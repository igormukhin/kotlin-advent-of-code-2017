import java.util.regex.Pattern

data class Program(val name: String, val weight: Int, val holds: List<String>) {
    companion object {
        val PATTERN = Regex("([a-z]+) \\((\\d+)\\)( -> (.+))*").toPattern()

        fun parse(line: String) : Program {
            val matcher = PATTERN.matcher(line)
            if (!matcher.matches()) throw Exception("Bad line: $line")
            val holds = matcher.group(4)?.split(", ") ?: emptyList()
            return Program(matcher.group(1), matcher.group(2).toInt(), holds)
        }
    }
}

fun main(args: Array<String>) {
    val programs = Utils.readInput("Advent7").lines().map { Program.parse(it) }
    val refs = programs.map { it.holds }.flatten().toSet()
    println(programs.find { !refs.contains(it.name) }?.name)
}
