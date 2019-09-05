package fc.home_work.revolut.network.dto


import com.google.gson.annotations.SerializedName
import kotlin.reflect.KVisibility

data class LatestCurrencyRatesResponseDTO(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: Rates
) {
    data class Rates (
        @SerializedName("AUD")
        val AUD: Double,
        @SerializedName("BGN")
        val BGN: Double,
        @SerializedName("BRL")
        val BRL: Double,
        @SerializedName("CAD")
        val CAD: Double,
        @SerializedName("CHF")
        val CHF: Double,
        @SerializedName("CNY")
        val CNY: Double,
        @SerializedName("CZK")
        val CZK: Double,
        @SerializedName("DKK")
        val DKK: Double,
        @SerializedName("GBP")
        val GBP: Double,
        @SerializedName("HKD")
        val HKD: Double,
        @SerializedName("HRK")
        val HRK: Double,
        @SerializedName("HUF")
        val HUF: Double,
        @SerializedName("IDR")
        val IDR: Double,
        @SerializedName("ILS")
        val ILS: Double,
        @SerializedName("INR")
        val INR: Double,
        @SerializedName("ISK")
        val ISK: Double,
        @SerializedName("JPY")
        val JPY: Double,
        @SerializedName("KRW")
        val KRW: Double,
        @SerializedName("MXN")
        val MXN: Double,
        @SerializedName("MYR")
        val MYR: Double,
        @SerializedName("NOK")
        val NOK: Double,
        @SerializedName("NZD")
        val NZD: Double,
        @SerializedName("PHP")
        val PHP: Double,
        @SerializedName("PLN")
        val PLN: Double,
        @SerializedName("RON")
        val RON: Double,
        @SerializedName("RUB")
        val RUB: Double,
        @SerializedName("SEK")
        val SEK: Double,
        @SerializedName("SGD")
        val SGD: Double,
        @SerializedName("THB")
        val THB: Double,
        @SerializedName("TRY")
        val TRY: Double,
        @SerializedName("USD")
        val USD: Double,
        @SerializedName("ZAR")
        val ZAR: Double
    ){
        fun getValueByCurrencyID(currencyID:String):Double?{
            this::class.members.forEach {
                if (it.visibility == KVisibility.PUBLIC) {
                    if(it.name == currencyID)
                        return it.call(this) as Double

                }
            }

            return null
        }
    }
}