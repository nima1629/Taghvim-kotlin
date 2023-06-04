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
    val doom = 3913142400000-1
    val poost = doom + 69971400000
    println("-----------------------------------------------")
    println(mez.toShamsi("10/1999/10", "MM/yyyy/dd").toString())

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


/*
۲۲ / ۱۲ / ۱۴۹۹
2121   March  13   , Thursday
13 - 3 - 2121


۲۲ / ۱۲ / ۱۴۳۰
2052   March  12   , Tuesday
12 - 3 - 2052
 */
