package fc.home_work.revolut

import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.ui.helper.CurrencyHelper
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class CurrencyHelperTest {

    private var currencyList = ArrayList<CurrencyModel>()


    @Before
    fun setUp(){
        val currencyMockBase = CurrencyModel("BASE")
        val currencyMockDouble = CurrencyModel("DOUBLE",0.5)
        val currencyMockHalf = CurrencyModel("HALF",2.0)

        currencyList.add(currencyMockBase)
        currencyList.add(currencyMockDouble)
        currencyList.add(currencyMockHalf)
    }

    @Test
    fun calculateNewBaseValueTest(){
        assertTrue(CurrencyHelper.calculateNewBaseValue(1.0, "10.0") == 10.0)
        assertTrue(CurrencyHelper.calculateNewBaseValue(0.5, "10.0") == 20.0)
        assertTrue(CurrencyHelper.calculateNewBaseValue(2.0, "10.0") == 5.0)
        assertTrue(CurrencyHelper.calculateNewBaseValue(1.0, "") == 0.0)
    }

    @Test(expected = Exception::class)
    fun calculateNewBaseValueThrowException(){
        CurrencyHelper.calculateNewBaseValue(1.0,"ABCDE")
    }

    @Test
    fun roundValueUpWorksFine(){

        val hugeDouble = 0.45000000000234
        val hugeDouble2 = 0.45000000000

        assertTrue(CurrencyHelper.roundDoubleValueTo2DecimalUp(hugeDouble).toString().length == 4)

        assertTrue(CurrencyHelper.roundDoubleValueTo2DecimalUp(hugeDouble)  == 0.46)

        assertTrue(CurrencyHelper.roundDoubleValueTo2DecimalUp(hugeDouble2)  == 0.45)

    }

    @Test
    fun findCurrencyRateByIDWorksFine(){

        val rateBase = CurrencyHelper.getCurrencyRateByID(currencyList, "BASE")
        val rateHalf = CurrencyHelper.getCurrencyRateByID(currencyList, "HALF")
        val rateDouble = CurrencyHelper.getCurrencyRateByID(currencyList, "DOUBLE")

        assertNotNull(rateBase)
        assertNotNull(rateHalf)
        assertNotNull(rateDouble)

        assertTrue(rateBase!!.equals(1.0))
        assertTrue(rateHalf!!.equals(2.0))
        assertTrue(rateDouble!!.equals(0.5))

        val rateThatDoesNotExist = CurrencyHelper.getCurrencyRateByID(currencyList, "NOT EXIST")
        assertNull(rateThatDoesNotExist)
    }


}