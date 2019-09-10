package fc.home_work.revolut.ui.helper

import fc.home_work.revolut.R
import fc.home_work.revolut.exception.NoDoubleException
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.util.CURRENCY_CAD
import fc.home_work.revolut.util.CURRENCY_EUR
import fc.home_work.revolut.util.CURRENCY_SEK
import fc.home_work.revolut.util.CURRENCY_USD
import java.lang.Exception
import java.math.RoundingMode

object CurrencyHelper {
    /**
     * Get flag image resource by CurrencyID
     */
    fun getCurrencyFlagResourceByID(currencyID:String):Int{
        return when(currencyID){
            CURRENCY_EUR -> R.drawable.icon_eu_flag
            CURRENCY_CAD -> R.drawable.icon_canada_flag
            CURRENCY_SEK -> R.drawable.icon_sweden_flag
            CURRENCY_USD -> R.drawable.icon_us_flag
            else -> { R.drawable.icon_eu_flag }
        }
    }

    /**
     * Get description resource by CurrencyID
     */
    fun getCurrencyDescriptionByID(currencyID:String):Int{
        return when(currencyID){
            CURRENCY_EUR -> R.string.currency_EUR_description
            CURRENCY_CAD -> R.string.currency_CAN_description
            CURRENCY_SEK -> R.string.currency_SEK_description
            CURRENCY_USD -> R.string.currency_USD_description
            else -> { R.string.currency_OTHER_description }
        }
    }

    /**
     * Return the BaseValue calculated from another currency (potentially) based on the currency amount and the currency rate
     * @return Double: new BaseValue
     * @exception: @NumberFormatException
     * @exception: @Exception
     */
    fun calculateNewBaseValue(currencyExchangeParam:Double, newValue:String) : Double {
        try {
            if(newValue.isNullOrEmpty()) return 0.0
            return ( newValue.toDouble() / currencyExchangeParam )
        }catch (noDouble:NumberFormatException){
            throw NoDoubleException()
        }catch (ex: Exception){
            throw ex
        }
    }

    fun roundDoubleValueTo2DecimalUp(doubleValue:Double): Double {
        return doubleValue.toBigDecimal().setScale(2, RoundingMode.UP ).toDouble()
    }

    /**
     * Return the value  with the ID passed as parameters if exist, null otherwise
     * @return Double: specific currency rate or NULL
     */
    fun getCurrencyRateByID(currencyList: ArrayList<CurrencyModel>, currencyID:String): Double? {
        for (element in currencyList){
            if(currencyID == element.id) return element.currencyExchangeParams
        }

        return null
    }

}