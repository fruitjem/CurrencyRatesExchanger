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


    fun updateCurrencyExchangerListWithNewRates(currencyList: ArrayList<CurrencyModel>, currencyExchangerList:ArrayList<CurrencyExchangerModel> ): ArrayList<CurrencyExchangerModel> {
        currencyExchangerList.forEach { t ->  t.updateRateValue(getCurrencyValueByID(currencyList, t.currency.id)) }
        return currencyExchangerList
    }

    fun updateCurrencyExchangerListWithNewBaseValue(currencyExchangerList:ArrayList<CurrencyExchangerModel>, newBaseValue:Double): ArrayList<CurrencyExchangerModel> {
        currencyExchangerList.forEach { t ->  t.calculateValue(newBaseValue) }
        return currencyExchangerList
    }


    fun getCurrencyValueByID(currencyList: ArrayList<CurrencyModel>, currencyID:String): Double? {
        for (element in currencyList){
            if(currencyID == element.id) return element.currencyExchangeParams
        }

        return null
    }

    fun calculateNewBaseValue(currencyExchangeParam:Double, newValue:String):Double{
        try {

            if(newValue.isNullOrEmpty()) return 0.0
            return ( newValue.toDouble() / currencyExchangeParam )

        }catch (noDouble:NumberFormatException){
            throw NoDoubleException()
        }catch (ex:Exception){
            throw ex
        }
    }

    fun moveElementInFirstPosition(currencyExchangerList:ArrayList<CurrencyExchangerModel>, position:Int): ArrayList<CurrencyExchangerModel> {
        val elementToSwap = currencyExchangerList[position]
        currencyExchangerList.removeAt(position)
        currencyExchangerList.add(0,elementToSwap )

        return currencyExchangerList
    }

    //This field should be returned by service
    fun getCurrencyFlagResourceByID(currencyID:String):Int{
        return when(currencyID){
            CURRENCY_EUR -> R.drawable.icon_eu_flag
            CURRENCY_CAD -> R.drawable.icon_canada_flag
            CURRENCY_SEK -> R.drawable.icon_sweden_flag
            CURRENCY_USD -> R.drawable.icon_us_flag
            else -> { R.drawable.icon_eu_flag }
        }
    }

    //This field should be returned by service
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