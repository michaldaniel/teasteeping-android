package eu.morningbird.teasteeping.utils

object Units {
    fun convertCelciusToFahrenheit(celsius: Int): Int {
        //And round up to nearest 5...
        return 5*(Math.round((celsius.toFloat() * 9f / 5f + 32f)/5f))
    }
}