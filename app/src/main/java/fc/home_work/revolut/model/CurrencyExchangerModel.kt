package fc.home_work.revolut.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import timber.log.Timber
import java.math.RoundingMode

data class CurrencyExchangerModel(var currency:CurrencyModel, @DrawableRes val currencyFlagResourceID:Int, @StringRes val currencyDescriptionResourceID:Int, var currentValue:Double = 0.0){

    private var lastBaseValue: Double

    init {
        lastBaseValue = currentValue
    }

    fun calculateValue(newValue:Double){
        lastBaseValue = newValue
        this.currentValue = (newValue * currency.currencyExchangeParams).toBigDecimal().setScale(2, RoundingMode.UP ).toDouble()
    }

    fun updateRateValue(newRate:Double?){
        if(newRate != null){
            currency.currencyExchangeParams = newRate
            calculateValue(lastBaseValue)
        }else{
            Timber.e("Currency ${currency.id} not well handled, check it")
        }

        Timber.d("new value for currency ${currency.id} --> with rate $newRate  [$currentValue]")

    }

    override fun toString(): String {
        return "Cuurrency:${currency.id} with rate: ${currency.currencyExchangeParams} has value $currentValue"
    }

    companion object{
        const val CURRENT_VALUE = "CURRENT_VALUE"
    }

}