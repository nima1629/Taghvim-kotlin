package date_types

import TaghvimImpl

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

    fun toMiladi():MiladiDate{
        val taghvim = TaghvimImpl()
        val date = taghvim.toMiladi(this)
        return MiladiDate(
            day = "${date[0]}${date[1]}".toInt(),
            month = "${date[3]}${date[4]}".toInt(),
            year = "${date[6]}${date[7]}${date[8]}${date[9]}".toInt(),
        )
    }
}