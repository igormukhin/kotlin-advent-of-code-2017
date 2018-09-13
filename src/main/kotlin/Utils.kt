
object Utils {
    fun readInput(dataSetName : String) : String {
        return this::class.java.getResource("/input/$dataSetName.txt").readText()
    }
}