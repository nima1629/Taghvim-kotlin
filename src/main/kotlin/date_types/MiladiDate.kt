package date_types

import TaghvimImpl

data class MiladiDate(
    val year: Int,
    val month: Int,
    val day:Int,
){
    init {
        require(year > 0)
        require(month in 1..12)
        require(day in 1..31)
    }

    override fun toString(): String {
        return "${this.day}/${this.month}/${this.year}"
    }

    fun toShamsi(): ShamsiDate {
        val taghvim= TaghvimImpl()
        return taghvim.toShamsi(year, month, day).date
    }

    fun toMiladiDateAndTime(hour: Int = 0, minute: Int = 0, second: Int =0): MiladiDateAndTime {
        return MiladiDateAndTime(date = this, time = Time24H(hour, minute, second))
    }

}



