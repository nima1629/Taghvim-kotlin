package date_types

import TaghvimImpl

data class ShamsiDateAndTime(
    val date: ShamsiDate,
    val time: Time24H,
){
    override fun toString(): String {
        return this.date.toString() + " " + this.time.toString()
    }
}
