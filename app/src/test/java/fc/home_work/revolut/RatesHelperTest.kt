package fc.home_work.revolut

import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.ui.RatesHelper
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.Exception


/**
 * Unit tests for the Rates / Currencies logic.
 */
class RatesHelperTest {

    private var currencyExchangeList = ArrayList<CurrencyExchangerModel>()
    private var currencyList = ArrayList<CurrencyModel>()
    private var updatedCurrencyList = ArrayList<CurrencyModel>()


    @Before
    fun setUp(){
        val currencyMockBase = CurrencyModel("BASE")
        val currencyMockDouble = CurrencyModel("DOUBLE",0.5)
        val currencyMockHalf = CurrencyModel("HALF",2.0)

        currencyList.add(currencyMockBase)
        currencyList.add(currencyMockDouble)
        currencyList.add(currencyMockHalf)

        val currencyMockDoubleUpdated = CurrencyModel("DOUBLE",0.1)
        val currencyMockHalfUpdated = CurrencyModel("HALF",10.0)

        updatedCurrencyList.add(currencyMockDoubleUpdated)
        updatedCurrencyList.add(currencyMockHalfUpdated)
    }


    @Test
    fun createBaseListHasTheCorrectNumberOfElement(){
        currencyExchangeList = RatesHelper.buildCurrencyExchangerList(currencyList)
        assertTrue(currencyExchangeList.size == currencyList.size)
    }

    @Test
    fun findCurrencyRateByIDWorksFine(){

        val rateBase = RatesHelper.getCurrencyRateByID(currencyList, "BASE")
        val rateHalf = RatesHelper.getCurrencyRateByID(currencyList, "HALF")
        val rateDouble = RatesHelper.getCurrencyRateByID(currencyList, "DOUBLE")

        assertNotNull(rateBase)
        assertNotNull(rateHalf)
        assertNotNull(rateDouble)

        assertTrue(rateBase!!.equals(1.0))
        assertTrue(rateHalf!!.equals(2.0))
        assertTrue(rateDouble!!.equals(0.5))

        val rateThatDoesNotExist = RatesHelper.getCurrencyRateByID(currencyList, "NOT EXIST")
        assertNull(rateThatDoesNotExist)
    }

    @Test
    fun updateExchangeListWithNewRatestIsConsistent(){
        currencyExchangeList = RatesHelper.buildCurrencyExchangerList(currencyList)
        currencyExchangeList = RatesHelper.updateCurrencyExchangerListWithNewRates(updatedCurrencyList,currencyExchangeList)

        assertTrue(currencyExchangeList.isNullOrEmpty().not())

        for (exchangeElement in currencyExchangeList){
            when(exchangeElement.currency.id){
                "HALF" ->  assertTrue(exchangeElement.currency.currencyExchangeParams == 10.0)
                "DOUBLE" ->  assertTrue(exchangeElement.currency.currencyExchangeParams == 0.1)
                "BASE" ->  assertTrue(exchangeElement.currency.currencyExchangeParams == 1.0)
                else -> assertTrue(false) //should never go HERE
            }
        }
    }

    @Test
    fun updateCurrencyExchangerListWithNewBaseValueIsConsistent(){
        currencyExchangeList = RatesHelper.buildCurrencyExchangerList(currencyList)

        //Update the List with new BaseValue
        currencyExchangeList = RatesHelper.updateCurrencyExchangerListWithNewBaseValue(currencyExchangeList, 1.0)

        assertTrue(currencyExchangeList.isNullOrEmpty().not())

        for (exchangeElement in currencyExchangeList){
            when(exchangeElement.currency.id){
                "HALF" ->  assertTrue(exchangeElement.currentValue == 2.0)
                "DOUBLE" ->  assertTrue(exchangeElement.currentValue == 0.5)
                "BASE" ->  assertTrue(exchangeElement.currentValue == 1.0)
                 else -> assertTrue(false) //should never go HERE
            }
        }

        //Update the List with new Rates
        currencyExchangeList = RatesHelper.updateCurrencyExchangerListWithNewRates(updatedCurrencyList,currencyExchangeList)

        for (exchangeElement in currencyExchangeList){
            when(exchangeElement.currency.id){
                "HALF" ->  assertTrue(exchangeElement.currentValue == 10.0)
                "DOUBLE" ->  assertTrue(exchangeElement.currentValue == 0.1)
                "BASE" ->  assertTrue(exchangeElement.currentValue == 1.0)
                else -> assertTrue(false) //should never go HERE
            }
        }

    }


    @Test
    fun calculateNewBaseValueTest(){
        assertTrue(RatesHelper.calculateNewBaseValue(1.0,"10.0") == 10.0)
        assertTrue(RatesHelper.calculateNewBaseValue(0.5,"10.0") == 20.0)
        assertTrue(RatesHelper.calculateNewBaseValue(2.0,"10.0") == 5.0)
        assertTrue(RatesHelper.calculateNewBaseValue(1.0,"") == 0.0)
    }

    @Test (expected = Exception::class)
    fun calculateNewBaseValueThrowException(){
        RatesHelper.calculateNewBaseValue(1.0,"ABCDE")
    }

    @Test
    fun movePositionToTopListTest(){

        val list = ArrayList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)

        val listWithFiveOnTop = RatesHelper.moveElementInFirstPosition(list as java.util.ArrayList<Int>,4)

        assertTrue(listWithFiveOnTop[0] == 5)
        assertTrue(listWithFiveOnTop[1] == 1)
        assertTrue(listWithFiveOnTop[2] == 2)
        assertTrue(listWithFiveOnTop[3] == 3)
        assertTrue(listWithFiveOnTop[4] == 4)


        val listWithThreeOnTop = RatesHelper.moveElementInFirstPosition(list as ArrayList<Int>,3)
        assertTrue(listWithThreeOnTop[0] == 3)


    }



}