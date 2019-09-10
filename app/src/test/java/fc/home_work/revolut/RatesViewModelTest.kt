package fc.home_work.revolut

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.repository.RatesRepository
import fc.home_work.revolut.ui.RatesViewModel
import fc.home_work.revolut.util.RxImmediateSchedulerRule
import fc.home_work.revolut.util.mock
import io.reactivex.Flowable.just
import io.reactivex.Observable
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class RatesViewModelTest {

    @get:Rule
    open val instantExecutorRule = InstantTaskExecutorRule()

    @Rule @JvmField
    open val timeoutRule = RxImmediateSchedulerRule()

    var observer: Observer<Pair<ArrayList<CurrencyExchangerModel>,Boolean>> = mock()

    var repository: RatesRepository = mock()

    var currencyList = ArrayList<CurrencyModel>()

    lateinit var viewModel : RatesViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        viewModel = RatesViewModel(repository)
        viewModel.getCurrencyExchangerObservableList().observeForever(observer)

        //Add one element to the list
        currencyList.add(CurrencyModel("BASE"))
    }

    @Test
    fun testObservable(){
        assertTrue(viewModel.getCurrencyExchangerObservableList().hasObservers())
    }

    @Test
    fun testObservableWhenDataChange(){
        Mockito.`when`(repository.getFlowableRates()).thenReturn(just(currencyList as List<CurrencyModel>))
        viewModel.observeCurrencyRatesChanges()
        Assert.assertEquals(1, viewModel.getCurrencyExchangerObservableList().value?.first?.size)
    }

    @Test
    fun testDatabaseEmptyWithOffline(){
        Mockito.`when`(repository.checkIfRatesDBTableIsEmpty()).thenReturn(Observable.just(true))
        viewModel.checkIfDatabaseIsEmptyAndShowAlert()
        Assert.assertEquals(R.string.user_is_offline_error, viewModel.getErrorEventObservable().value)
    }

    @Test
    fun testDatabaseIsNotEmptyWithOffline(){
        Mockito.`when`(repository.checkIfRatesDBTableIsEmpty()).thenReturn(Observable.just(false))
        viewModel.checkIfDatabaseIsEmptyAndShowAlert()
        Assert.assertEquals(R.string.user_is_offline_warning, viewModel.getErrorEventObservable().value)
    }


}