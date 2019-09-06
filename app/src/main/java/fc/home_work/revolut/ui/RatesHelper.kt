package fc.home_work.revolut.ui

import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel

object RatesHelper {

    fun buildCurrencyExchangerList(currencyList: ArrayList<CurrencyModel>): ArrayList<CurrencyExchangerModel> {
        val currExcList = ArrayList<CurrencyExchangerModel>()
        for (currencyModel in currencyList) {
            currExcList.add(CurrencyExchangerModel(currencyModel))
        }
        return currExcList
    }


    fun updateCurrencyExchangerList(currencyList: ArrayList<CurrencyModel>, currencyExchangerList:ArrayList<CurrencyExchangerModel> ): ArrayList<CurrencyExchangerModel> {
        currencyExchangerList.forEach { t ->  t.updateValue(getCurrencyValueByID(currencyList, t.currency.id)) }
        return currencyExchangerList
    }


    fun getCurrencyValueByID(currencyList: ArrayList<CurrencyModel>, currencyID:String): Double? {
        for (element in currencyList){
            if(currencyID == element.id) return element.currencyExchangeParams
        }

        return null
    }

}