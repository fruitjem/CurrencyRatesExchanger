package fc.home_work.revolut

import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.ui.helper.CurrencyExchagerHelper
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the CurrencyExchangeHelper logic.
 */
class CurrencyExchagerHelperTest {

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
        currencyExchangeList = CurrencyExchagerHelper.buildCurrencyExchangerList(currencyList)
        assertTrue(currencyExchangeList.size == currencyList.size)
    }

    @Test
    fun updateExchangeListWithNewRatestIsConsistent(){
        currencyExchangeList = CurrencyExchagerHelper.buildCurrencyExchangerList(currencyList)
        currencyExchangeList = CurrencyExchagerHelper.updateCurrencyExchangerListWithNewRates(updatedCurrencyList,currencyExchangeList)

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
        currencyExchangeList = CurrencyExchagerHelper.buildCurrencyExchangerList(currencyList)

        //Update the List with new BaseValue
        currencyExchangeList = CurrencyExchagerHelper.updateCurrencyExchangerListWithNewBaseValue(currencyExchangeList, 1.0)

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
        currencyExchangeList = CurrencyExchagerHelper.updateCurrencyExchangerListWithNewRates(updatedCurrencyList,currencyExchangeList)

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
    fun movePositionToTopListTest(){

        val list = ArrayList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)

        //test the top element and the shift
        val listWithFiveOnTop = CurrencyExchagerHelper.moveElementInFirstPosition(list,4)

        assertTrue(listWithFiveOnTop[0] == 5)
        assertTrue(listWithFiveOnTop[1] == 1)
        assertTrue(listWithFiveOnTop[2] == 2)
        assertTrue(listWithFiveOnTop[3] == 3)
        assertTrue(listWithFiveOnTop[4] == 4)

        //test the top element
        val listWithThreeOnTop = CurrencyExchagerHelper.moveElementInFirstPosition(list,3)
        assertTrue(listWithThreeOnTop[0] == 3)

    }


}