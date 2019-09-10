package fc.home_work.revolut

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import fc.home_work.revolut.database.RevolutDatabase
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.ui.helper.CurrencyExchagerHelper
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class RatesDaoTest {

    @Rule
    @JvmField
    open val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var revolutDatabase: RevolutDatabase

    private var currencyList = ArrayList<CurrencyModel>()


    @Before
    fun initDb() {
        revolutDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RevolutDatabase::class.java).allowMainThreadQueries().build()

        val currencyMockBase = CurrencyModel("BASE")
        val currencyMockDouble = CurrencyModel("DOUBLE",0.5)
        val currencyMockHalf = CurrencyModel("HALF",2.0)

        currencyList.add(currencyMockBase)
        currencyList.add(currencyMockDouble)
        currencyList.add(currencyMockHalf)
    }

    @After
    fun closeDb() {
        revolutDatabase.close()
    }

    @Test
    fun emptyDatabaseReturnEmptyList() {

       revolutDatabase.currencyRatesDAO().getAll().test().assertValue {
            it.isEmpty()
        }
    }

    @Test
    fun saveCurrencyItemsOnDatabaseAndGetThemBack() {

        revolutDatabase.currencyRatesDAO().insertAll(currencyList).test()

        revolutDatabase.currencyRatesDAO().getAll().test()
            .assertValue {
                it.isNotEmpty()
            }
            .assertValue { CurrencyExchagerHelper.getCurrencyRateByID(it as ArrayList<CurrencyModel>,"BASE") != null }
            .assertValue { CurrencyExchagerHelper.getCurrencyRateByID(it as ArrayList<CurrencyModel>,"DOUBLE") != null }
            .assertValue { CurrencyExchagerHelper.getCurrencyRateByID(it as ArrayList<CurrencyModel>,"HALF") != null }
            .assertValue { CurrencyExchagerHelper.getCurrencyRateByID(it as ArrayList<CurrencyModel>,"ABCDE") == null }

    }
}