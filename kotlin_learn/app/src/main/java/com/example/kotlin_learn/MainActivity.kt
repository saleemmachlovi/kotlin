import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(){
        runBlocking {
            try {
                getWeather()
            }catch (e: AssertionError){
                println("Error ${
                    e.message
                }")
            }
        }

}

suspend fun getWeather() = coroutineScope {
    val temp = async {getTemperature()}
    val fore = async { getForeCast()}
    if (fore.isActive){
        fore.cancel()
    }
}

suspend fun getForeCast() {
    delay(1000)
    val check: Boolean = false
    if (check != true) {
        println("ForeCast is good")
    }else{
        throw AssertionError()
    }
}
suspend fun getTemperature(){
    println("Temperature is good")
}