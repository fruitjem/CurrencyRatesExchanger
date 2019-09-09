package fc.home_work.revolut.ui

import fc.home_work.revolut.R
import fc.home_work.revolut.exception.NoDoubleException
import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.util.CURRENCY_CAD
import fc.home_work.revolut.util.CURRENCY_EUR
import fc.home_work.revolut.util.CURRENCY_SEK
import fc.home_work.revolut.util.CURRENCY_USD
import java.lang.Exception

object RatesHelper {

    /**
     * Create a CurrencyExchangeList with default value = 0
     * @params: currencyList ArrayList of currencies model
     * @return  ArrayList<CurrencyExchangeList>
     */
    fun buildCurrencyExchangerList(currencyList: ArrayList<CurrencyModel>): ArrayList<CurrencyExchangerModel> {
        val currExcList = ArrayList<CurrencyExchangerModel>()
        for (currencyModel in currencyList) {
            currExcList.add(
                CurrencyExchangerModel(
                    currencyModel,
                    getCurrencyFlagResourceByID(currencyModel.id),
                    getCurrencyDescriptionByID(currencyModel.id)
            ))
        }
        return currExcList
    }


    /**
     * Update the <currencyExchangerList> with the new currencies rates values but the same baseValue
     * @return updated list
     */
    fun updateCurrencyExchangerListWithNewRates(currencyList: ArrayList<CurrencyModel>, currencyExchangerList:ArrayList<CurrencyExchangerModel> ): ArrayList<CurrencyExchangerModel> {
        currencyExchangerList.forEach { t ->  t.updateRateValue(getCurrencyRateByID(currencyList, t.currency.id)) }
        return currencyExchangerList
    }

    /**
     * Update the <currencyExchangerList> with the new base value but the same currency rate
     * @return updated list
     */
    fun updateCurrencyExchangerListWithNewBaseValue(currencyExchangerList:ArrayList<CurrencyExchangerModel>, newBaseValue:Double): ArrayList<CurrencyExchangerModel> {
        currencyExchangerList.forEach { t ->  t.calculateValue(newBaseValue) }
        return currencyExchangerList
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
        }catch (ex:Exception){
            throw ex
        }
    }

    /**
     * Swap the element at position <position> in top of the List and scale by 1 position the others
     */
    fun <T> moveElementInFirstPosition(list:ArrayList<T>, position:Int): ArrayList<T> {
        val elementToSwap = list[position]
        list.removeAt(position)
        list.add(0,elementToSwap )
        return list
    }


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

}