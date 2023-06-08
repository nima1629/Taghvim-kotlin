package date_types

data class ShamsiDateAndTime(
    val date: ShamsiDate,
    val time: Time24H,
){
    override fun toString(): String {
        return this.date.toString() + " " + this.time.toString()
    }

    fun toMiladi():MiladiDateAndTime{
        return MiladiDateAndTime(this.date.toMiladi(), this.time)
    }
}
