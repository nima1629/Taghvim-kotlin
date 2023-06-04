import date_types.ShamsiDate
import date_types.ShamsiDateAndTime
import date_types.Time24H
import java.time.*
import java.time.format.DateTimeFormatter

class TaghvimImpl():Taghvim{
    private companion object{
        val LEAP_YEARS by lazy { listOf(1354, 1358, 1362, 1366,1370,1375,1379,1383,1387,1391,1395,1399,1403,1408,1412,1416,1420,1424,1428,
            1432, 1436, 1441, 1445, 1449, 1453, 1457, 1461, 1465, 1469, 1474, 1478, 1482, 1486, 1490, 1494, 1498) }
        const val MILLIS_IN_DAY:Long = 86400000
        const val MILLIS_IN_YEAR:Long = 31536000000
        const val MILLIS_IN_HOUR:Long = 3600000
        const val MILLIS_IN_FOUR_KABISE = 126144000000 + MILLIS_IN_DAY
        const val BEGINNING_MILLIS:Long = 69971400000  //millis of the date 21 - 3 - 1972 or ۱ / ۱ / ۱۳۵۱
    }
    private val currentDate by lazy { toShamsi(System.currentTimeMillis()).date }

    val currentDayInCalender by lazy { toDay(toShamsi(System.currentTimeMillis()).date) }
    val currentDateAndTime by lazy { toShamsi(System.currentTimeMillis()) }
    val currentDay by lazy { currentDate.day }
    val currentMonth by lazy { currentDate.month }
    val currentYear by lazy { currentDate.year }

    private fun dateInOneKabiseCycle(millis: Long, kabiseYear:Int = 4, beginningYear:Int = 0): ShamsiDate {
        if (millis >= MILLIS_IN_YEAR*kabiseYear){
            return ShamsiDate(kabiseYear+beginningYear,12,30)
        }
        val year = (millis/ MILLIS_IN_YEAR).toInt() +1
        val remainingMillisForDay = millis% MILLIS_IN_YEAR
        val dayInYear:Int = (remainingMillisForDay/ MILLIS_IN_DAY).toInt()
        return if (dayInYear > 186){
            val dayInNext6Month = dayInYear - 186
            val month = (dayInNext6Month/30) + 7
            val day = (dayInNext6Month%30) + 1
            ShamsiDate(year+beginningYear, month, day)
        } else {
            val month = (dayInYear/31) + 1
            val day = (dayInYear%31) + 1
            ShamsiDate(year+beginningYear, month, day)
        }
    }

    private fun dateInMultipleKabiseCycles(millis: Long,beginningYear:Int): ShamsiDate {
        val numberOfKabises = (millis / MILLIS_IN_FOUR_KABISE).toInt() *4
        val remainingMillis = millis % MILLIS_IN_FOUR_KABISE
        val date = dateInOneKabiseCycle(remainingMillis)
        return ShamsiDate(beginningYear+numberOfKabises+date.year, date.month, date.day)
    }

    private fun dayInYear(month: Int, day: Int):Int{
        return if (month < 7){
            ((month-1)*31) + day
        } else {
            ((month-7)*30) + day + 186
        }
    }


    override fun toShamsi(millis:Long): ShamsiDateAndTime {
        //Check if millis is in range:
        if (millis < BEGINNING_MILLIS || millis > 4835017800000L){
            return ShamsiDateAndTime(
                date = ShamsiDate(0,0,0),
                time = Time24H(0,0,0)
            )
        }
        val millisCorrected = millis-BEGINNING_MILLIS

        //Time:
        val hour = (millisCorrected%MILLIS_IN_DAY)/MILLIS_IN_HOUR
        val remainingMillisForMinute = (millisCorrected%MILLIS_IN_DAY)%MILLIS_IN_HOUR
        val minute = remainingMillisForMinute/60000
        val remainingMillisForSecond = remainingMillisForMinute%60000
        val second = remainingMillisForSecond/1000

        //Date:
        val date: ShamsiDate = when(millisCorrected){
            in 0 until 631152000000 -> {
                dateInMultipleKabiseCycles(millisCorrected, 1350)
            } //4
            in 631152000000 until 788918400000 -> {
                dateInOneKabiseCycle(millis = millisCorrected - 631152000000, kabiseYear = 5, beginningYear = 1370)
            } //5
            in 788918400000 until 1672531200000 -> {
                dateInMultipleKabiseCycles(millisCorrected - 788918400000, 1375)
            } //4
            in 1672531200000 until 1830297600000 -> {
                dateInOneKabiseCycle(millis = millisCorrected - 1672531200000, kabiseYear = 5, beginningYear = 1403)
            } //5
            in 1830297600000 until 2713910400000 -> {
                dateInMultipleKabiseCycles(millisCorrected - 1830297600000, 1408)
            } //4
            in 2713910400000 until 2871676800000 -> {
                dateInOneKabiseCycle(millis = millisCorrected - 2713910400000, kabiseYear = 5, beginningYear = 1436)
            } //5
            in 2871676800000 until 3755289600000 -> {
                dateInMultipleKabiseCycles(millisCorrected - 2871676800000, 1441)
            } //4
            in 3755289600000 until 3913056000000 -> {
                dateInOneKabiseCycle(millis = millisCorrected - 3755289600000, kabiseYear = 5, beginningYear = 1469)
            } //5
            else -> {
                dateInMultipleKabiseCycles(millisCorrected - 3913056000000, 1474)
            }
        }



        return ShamsiDateAndTime(
            date = date,
            time = Time24H(hour.toInt(),minute.toInt(),second.toInt())
        )
    }

    override fun toShamsi(date:String, format:String): ShamsiDateAndTime { //formattedString
        return toShamsi(toMillis(date, format))
    }

    override fun toShamsi(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): ShamsiDateAndTime {
        val millis = toMillis(year, month, day,hour,minute,second)
        return toShamsi(millis)
    }

    override fun toShamsi(day: Int): ShamsiDate {
        val millis = (day * MILLIS_IN_DAY) + BEGINNING_MILLIS
        return toShamsi(millis).date
    }

    override fun toDay(shamsiDate: ShamsiDate): Int {
        val daysThisYear = dayInYear(shamsiDate.month, shamsiDate.day)
        val extraDaysInPastYears = LEAP_YEARS.filter { it < shamsiDate.year }.size
        return daysThisYear+extraDaysInPastYears+((shamsiDate.year - 1351)*365)-1
    }

    override fun toDay(millis: Long): Int {
        TODO("Not yet implemented")
    }

    override fun toDay(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Int {
        TODO("Not yet implemented")
    }

    override fun toDay(date:String, format:String): Int {
        TODO("Not yet implemented")
    }

    override fun toMiladi(shamsiDate: ShamsiDate): String {
        TODO("Not yet implemented")
    }

    override fun toMiladi(millis: Long): String { //fromMillis
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        val instant = Instant.ofEpochMilli(millis)
        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return formatter.format(date)
    }

    override fun toMiladi(shamsiDateAndTime: ShamsiDateAndTime): String {
        TODO("Not yet implemented")
    }

    override fun toMiladi(dayInShamsiCalendar: Int): String {
        val date = toShamsi(dayInShamsiCalendar)
        return toMiladi(date)
    }

    override fun toMillis(date:String, format:String): Long {
        val formatNew = if ("HH" in format) format else "$format HH"
        val dateNew = if ("HH" in format) date else "$date 00"
        val formatter = DateTimeFormatter.ofPattern(formatNew)
        val localDate = LocalDateTime.parse(dateNew, formatter)
        return localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()-12600000
    }

    override fun toMillis(dayInShamsiCalendar: Int): Long {
        TODO("Not yet implemented")
    }

    override fun toMillis(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Long {
        val yearS = year.toString()
        val monthS = if (month>9) month.toString() else "0$month"
        val dayS = if (day>9) day.toString() else "0$day"
        val hourS = if (hour>9) hour.toString() else "0$hour"
        val minuteS = if(minute>9) minute.toString() else "0$minute"
        val secondS = if (second>9) second.toString() else "0$second"
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        val localDate = LocalDateTime.parse("$dayS/$monthS/$yearS $hourS:$minuteS:$secondS", formatter)
        return localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()-12600000
    }

    override fun toMillis(shamsiDate: ShamsiDate): Long {
        TODO("Not yet implemented")
    }

    override fun toMillis(shamsiDateAndTime: ShamsiDateAndTime): Long {
        TODO("Not implemented")
    }
}






