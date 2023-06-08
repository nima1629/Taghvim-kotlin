package date_types

data class MiladiDateAndTime(
    val date: MiladiDate,
    val time: Time24H,
){
    override fun toString(): String {
        return this.date.toString() + " " + this.time.toString()
    }

    fun toShamsi():ShamsiDateAndTime{
        return ShamsiDateAndTime(this.date.toShamsi(), this.time)
    }
}