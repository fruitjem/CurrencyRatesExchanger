package fc.home_work.revolut

import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.ui.RatesHelper
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CurrencyExchangeModelTest {

    private lateinit var currencyExchangeModelBase : CurrencyExchangerModel

    @Before
    fun initModel(){

        val currencyMockBase = CurrencyModel("USD", 2.0)

        currencyExchangeModelBase = CurrencyExchangerModel(
            currencyMockBase,
            RatesHelper.getCurrencyFlagResourceByID("USD"),
            RatesHelper.getCurrencyDescriptionByID("USD"))

    }

    @Test
    fun updateModelByNewBaseValue(){
        currencyExchangeModelBase.calculateValue(10.0)
        assertTrue(currencyExchangeModelBase.currentValue == 20.0)
    }

    @Test
    fun updateModelByNewRateValue(){

        currencyExchangeModelBase.calculateValue(10.0) //set the baseValue to 10
        currencyExchangeModelBase.updateRateValue(1.0) //set the rate to 1.0 (as BaseRate)
        assertTrue(currencyExchangeModelBase.currentValue == 10.0)

        currencyExchangeModelBase.updateRateValue(2.0) //set the rate to 2.0
        assertTrue(currencyExchangeModelBase.currentValue == 20.0)

        currencyExchangeModelBase.updateRateValue(0.5) //set the rate to 0.5
        assertTrue(currencyExchangeModelBase.currentValue == 5.0)
    }
}