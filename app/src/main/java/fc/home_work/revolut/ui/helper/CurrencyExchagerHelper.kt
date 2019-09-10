package fc.home_work.revolut.ui.helper


import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel

object CurrencyExchagerHelper {

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
                    CurrencyHelper.getCurrencyFlagResourceByID(currencyModel.id),
                    CurrencyHelper.getCurrencyDescriptionByID(currencyModel.id)
            ))
        }
        return currExcList
    }


    /**
     * Update the <currencyExchangerList> with the new currencies rates values but the same baseValue
     * @return updated list
     */
    fun updateCurrencyExchangerListWithNewRates(currencyList: ArrayList<CurrencyModel>, currencyExchangerList:ArrayList<CurrencyExchangerModel> ): ArrayList<CurrencyExchangerModel> {
        currencyExchangerList.forEach { t ->  t.updateRateValue(CurrencyHelper.getCurrencyRateByID(currencyList, t.currency.id)) }
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
     * Swap the element at position <position> in top of the List and scale by 1 position the others
     */
    fun <T> moveElementInFirstPosition(list:ArrayList<T>, position:Int): ArrayList<T> {
        val elementToSwap = list[position]
        list.removeAt(position)
        list.add(0,elementToSwap )
        return list
    }

}