import date_types.MiladiDate
import date_types.MiladiDateAndTime
import date_types.Time24H
import java.lang.Exception

fun main() {

    /*
    val millis = miladiTomillis("2023","05","18","22","34","07")
    val test = millisToShamsiTest(millis)
    println("millis: $millis\n$test")

    println("-----------------")
    println(System.currentTimeMillis())
     */
    val mez = TaghvimImpl()
    println("-----------------------------------------------")

    val doom = MiladiDateAndTime(MiladiDate(1999, 4,12), Time24H(15, 53,23))
    val poost = doom.toShamsi()
    println(poost)
    println(poost.toMiladi())

}

fun test(){
    val mez = TaghvimImpl()
    var day = 0
    var result = "Successful"
    var exception = ""
    try {
        println(day)
        while (day < 55150){
            if (mez.toShamsi(69971400000L + (day*86400000L)).date == mez.toShamsi(day)){
                day++
                result = "Successful at day: $day"
            } else{
                result = "failed at day $day"
                break
            }
        }
    } catch (e:Exception){
        result = "Exception happened at day $day"
        exception = e.toString()
    }
    println(result + "\n" + exception)
}

fun test2(){
    var day = 0
    var result = ""
    val mez = TaghvimImpl()
    while (day < 55150){
        if (day == 30000){
            println(day)
            println(result)
        }
        if (mez.toDay(mez.toShamsi(day)) == day){
            day++
        } else{
            result = "failed at day $day"
            break
        }
        result = "Successful at day $day"
    }
    println(result)
}

fun test3(){
    var day = 0
    var result = ""
    val mez = TaghvimImpl()
    while (day < 55150){
        if (mez.toDay(mez.toMillis(mez.toShamsi(day))) == day){
            day++
            result = "Successful at day $day"
        } else {
            result = "failed at day $day"
            break
        }

    }
    println(result)
}


