data class ShamsiDateAndTime(
    val date: ShamsiDate,
    val time: Time24H,
){
    override fun toString(): String {
        return this.date.toString() + " " + this.time.toString()
    }
}

data class MiladiDateAndTime(
    val date: MiladiDate,
    val time: Time24H,
)

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
}

data class Time24H(
    val hour:Int,
    val minute:Int,
    val second: Int,
){
    init {
        require(hour in 0..23)
        require(minute in 0..59)
        require(second in 0..59)
    }

    override fun toString(): String {
        return "${if (this.hour<10) "0${this.hour}" else this.hour}:${if (this.minute<10) "0${this.minute}" else this.minute}:${if (this.second<10) "0${this.second}" else this.second}"
    }
}

fun MiladiDate.toShamsi():ShamsiDate{
    val taghvim= TaghvimImpl()
    return taghvim.toShamsi(year, month, day).date
}

fun MiladiDate.toMiladiDateAndTime(hour: Int = 0, minute: Int = 0, second: Int =0):MiladiDateAndTime{
    return MiladiDateAndTime(date = this, time = Time24H(hour, minute, second))
}
