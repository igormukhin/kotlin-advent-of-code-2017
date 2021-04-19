import kotlin.math.*

fun main() {
    val input = Utils.readInput(20)
    val particles = input.lines().asSequence().filter { it.isNotEmpty() }.map(::parseParticle).toList()

    // taskA
    run {
        val min = particles.minOfWith(
            Comparator.comparing { p: Particle -> abs(p.x.a) + abs(p.y.a) + abs(p.z.a) }
                .thenComparing { p: Particle -> p.x.v.sign(p.x.a) + p.y.v.sign(p.y.a) + p.z.v.sign(p.z.a) }
                .thenComparing { p: Particle -> p.x.p.sign(p.x.a) + p.y.p.sign(p.y.a) + p.z.p.sign(p.z.a) }) { it }
        println("A: ${particles.indexOf(min)}")
    }

    // taskB
    run {
        var list = particles
        do {
            var time = Int.MAX_VALUE
            for (i in 0..(list.size - 2)) {
                for (j in (i + 1) until list.size) {
                    val p1 = listOf(list[i].x, list[i].y, list[i].z)
                    val p2 = listOf(list[j].x, list[j].y, list[j].z)

                    for (k in 0..2) {
                        val a = (p1[k].a - p2[k].a) / 2.0
                        val b = (p1[k].v.toDouble() - p2[k].v) + (p1[k].a - p2[k].a) / 2.0
                        val c = p1[k].p.toDouble() - p2[k].p
                        if (a != 0.0 || b != 0.0 || c != 0.0) {
                            val times = solve(a, b, c)
                                .filter { it >= 0.0 && floor(it) == ceil(it) }
                                .map(Number::toInt)
                            times.forEach { t ->
                                val pos1 = list[i].posAt(t)
                                val pos2 = list[j].posAt(t)
                                if (pos1 == pos2) time = min(time, t)
                            }
                            break
                        }
                    }
                }
            }

            if (time == Int.MAX_VALUE) {
                break
            }

            list = list.groupBy { it.posAt(time) }
                       .filter { it.value.size == 1 }
                       .flatMap { it.value }
                       .toList()

        } while (true)

        println("B: ${list.size}")
    }
}

fun solve(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        if (b == 0.0) {
            check(c != 0.0) { "all are 0" }
            return listOf()
        }
        return listOf(-c / b)
    }

    val d = b * b - 4L * a * c
    return when {
        d < 0 -> listOf()
        d == 0.0 -> listOf(-b / (2 * a))
        else -> listOf((sqrt(d) - b) / (2 * a), (-sqrt(d) - b) / (2 * a))
    }
}

private fun Int.sign(x: Int): Int =
    if ((this >= 0 && x >= 0) || (this <= 0 && x <= 0)) abs(this) else -abs(this)

private fun parseParticle(line: String): Particle {
    val (p, v, a) = line.split(", ")
        .map { it.substringAfter("<").substringBeforeLast(">") }
        .map { it.split(",") }
        .map { (x, y, z) -> Point3D(x.toInt(), y.toInt(), z.toInt()) }
    return Particle(Vector(p.x, v.x, a.x), Vector(p.y, v.y, a.y), Vector(p.z, v.z, a.z))
}

private data class Vector(var p: Int, var v: Int, var a: Int)

private data class Particle(var x: Vector, var y: Vector, var z: Vector) {
    fun posAt(t: Int): Point3D {
        val tt2 = t * (t + 1) / 2.0
        return Point3D((x.a * tt2 + x.v * t + x.p).toInt(),
            (y.a * tt2 + y.v * t + y.p).toInt(),
            (z.a * tt2 + z.v * t + z.p).toInt())
    }
}
