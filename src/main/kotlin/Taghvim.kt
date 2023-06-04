import date_types.ShamsiDate
import date_types.ShamsiDateAndTime

interface Taghvim {

    //toShamsi, from millis,string(miladi date), day(Int)
    fun toShamsi(millis:Long): ShamsiDateAndTime
    fun toShamsi(year:Int, month:Int, day:Int, hour:Int=0, minute:Int=0, second:Int=0): ShamsiDateAndTime
    fun toShamsi(date:String, format:String="dd/MM/yyyy HH:mm:ss"): ShamsiDateAndTime
    fun toShamsi(day:Int): ShamsiDate

    //get shamsi day:
    fun toDay(shamsiDate: ShamsiDate):Int
    fun toDay(millis: Long):Int
    fun toDay(year:Int, month:Int, day:Int, hour:Int=0, minute:Int=0, second:Int=0):Int
    fun toDay(date:String, format:String="dd/MM/yyyy HH:mm:ss"):Int

    //to miladi
    fun toMiladi(shamsiDate: ShamsiDate):String
    fun toMiladi(millis: Long): String
    fun toMiladi(shamsiDateAndTime: ShamsiDateAndTime):String
    fun toMiladi(dayInShamsiCalendar:Int):String

    //to millis
    fun toMillis(dayInShamsiCalendar:Int):Long
    fun toMillis(year:Int, month:Int, day:Int, hour:Int=0, minute:Int=0, second:Int=0):Long
    fun toMillis(date:String, format:String="dd/MM/yyyy HH:mm:ss"):Long
    fun toMillis(shamsiDate: ShamsiDate):Long
    fun toMillis(shamsiDateAndTime: ShamsiDateAndTime):Long

}