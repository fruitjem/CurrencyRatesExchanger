package fc.home_work.revolut.model

data class CurrencyExchangerModel(var currency:CurrencyModel, var currentValue:Double = 0.0){

    fun calculateValue(newValue:Double){
        this.currentValue = newValue * currency.currencyExchangeParams
    }

}