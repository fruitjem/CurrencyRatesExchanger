package fc.home_work.revolut.model

import timber.log.Timber
import java.math.RoundingMode

data class CurrencyExchangerModel(var currency:CurrencyModel, var currentValue:Double = 10.0){

    fun calculateValue(newValue:Double){
        this.currentValue = (newValue * currency.currencyExchangeParams).toBigDecimal().setScale(2, RoundingMode.UP ).toDouble()
    }

    fun updateValue(newRate:Double?){
        if(newRate != null){
            currency.currencyExchangeParams = newRate
            calculateValue(10.0)
        }else{
            Timber.e("Currency ${currency.id} not well handled, check it")
        }

        Timber.d("new value for currency ${currency.id} --> with rate $newRate  [$currentValue]")

    }

}