
fun main() {
    val input = Utils.readInput(15)
    val (startA, startB) = input.lines().map { it.substringAfterLast(' ') }.map(String::toLong)

    val facA = 16807L
    val facB = 48271L
    val div = 2147483647L

    // task A
    run {
        var varA = startA
        var varB = startB
        var counter = 0
        repeat(40_000_000) {
            varA = varA * facA % div
            varB = varB * facB % div
            if (varA % 65536 == varB % 65536) counter++
        }
        println("A: $counter")
    }

    // task B
    run {
        var varA = startA
        var varB = startB
        var counter = 0
        repeat(5_000_000) {
            do {
                varA = varA * facA % div
            } while (varA % 4 != 0L)

            do {
                varB = varB * facB % div
            } while (varB % 8 != 0L)

            if (varA % 65536 == varB % 65536) counter++
        }
        println("B: $counter")
    }
}




