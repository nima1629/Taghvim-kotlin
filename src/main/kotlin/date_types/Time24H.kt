package date_types

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

    val millis by lazy { hour* 3600000 + minute*60000 + second*1000}

    override fun toString(): String {
        return "${if (this.hour<10) "0${this.hour}" else this.hour}:${if (this.minute<10) "0${this.minute}" else this.minute}:${if (this.second<10) "0${this.second}" else this.second}"
    }
}