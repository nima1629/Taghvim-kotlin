package date_types

data class ShamsiDate(
    val year: Int,
    val month: Int,
    val day:Int,
){
    init {
        require(year > 0)
        require(month in 1..12)
        require(if (month<7)day in 1..31 else day in 1..30)

    }

    override fun toString(): String {
        return "${this.day}/${this.month}/${this.year}"
    }
}