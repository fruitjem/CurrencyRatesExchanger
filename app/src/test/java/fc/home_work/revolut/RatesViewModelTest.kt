package fc.home_work.revolut

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.repository.RatesRepository
import fc.home_work.revolut.ui.RatesViewModel
import io.reactivex.Flowable
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class RatesViewModelTest {

    @Rule
    @JvmField
    open val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    var observer: Observer<Pair<ArrayList<CurrencyExchangerModel>,Boolean>>? = null

    @Mock
    private lateinit var repository: RatesRepository

    private lateinit var viewModel : RatesViewModel

    private var currencyList = ArrayList<CurrencyModel>()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        viewModel = RatesViewModel(repository)
        viewModel.getCurrencyExchangerObservableList().observeForever(observer!!)
    }

    @Test
    fun testNull(){
        Mockito.`when`(repository.getFlowableRates()).thenReturn( Flowable.just(currencyList) )
        assertTrue(viewModel.getCurrencyExchangerObservableList().hasObservers())
    }
}