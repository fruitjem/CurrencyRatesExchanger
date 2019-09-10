package fc.home_work.revolut.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import fc.home_work.revolut.ui.RatesHelper
import timber.log.Timber

data class CurrencyExchangerModel(var currency:CurrencyModel, @DrawableRes val currencyFlagResourceID:Int, @StringRes val currencyDescriptionResourceID:Int, var currentValue:Double = 0.0){

    private var lastBaseValue: Double

    init {
        lastBaseValue = currentValue
    }

    /**
     * On <newBaseValue> update its current value
     **/
    fun calculateValue(newBaseValue:Double){
        lastBaseValue = newBaseValue
        this.currentValue = RatesHelper.roundDoubleValueTo2DecimalUp(newBaseValue * currency.currencyExchangeParams)
    }

    /**
     * On <newRateValue> update its current value
     **/
    fun updateRateValue(newRate:Double?){
        if(newRate != null){
            currency.currencyExchangeParams = newRate
            calculateValue(lastBaseValue)
        }else{
            Timber.e("Currency ${currency.id} not well handled, check it")
        }

        Timber.d(toString())
    }

    override fun toString(): String {
        return "Currency:${currency.id} with rate: ${currency.currencyExchangeParams} has value $currentValue"
    }

    companion object {
        const val CURRENT_VALUE = "CURRENT_VALUE"
    }

}