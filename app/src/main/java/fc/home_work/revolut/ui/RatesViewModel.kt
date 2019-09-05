package fc.home_work.revolut.ui

import androidx.lifecycle.MutableLiveData
import fc.home_work.revolut.base.BaseViewModel
import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.repository.RatesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class RatesViewModel : BaseViewModel() {

    @Inject
    lateinit var ratesRepo: RatesRepository

    //Local Data
    private lateinit var lastCurrencyList: ArrayList<CurrencyModel>
    private lateinit var lastCurrencyExchangerList: ArrayList<CurrencyExchangerModel>

    //Live Data
    private val currencyExchangerData: MutableLiveData<ArrayList<CurrencyExchangerModel>> = MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        loadData()
    }

    fun getCurrencyExchangerObservableList(): MutableLiveData<ArrayList<CurrencyExchangerModel>> {
        return currencyExchangerData
    }

    private fun loadData() {

        subscription.add(
            ratesRepo.loadCurrencyRatesFromAPI().toObservable()
                .subscribeOn(Schedulers.io())
                .doOnNext { lastCurrencyList = it }
                .map {
                    buildCurrencyExchangerList(it)
                }
                .doOnNext { lastCurrencyExchangerList = it }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoader(true) }
                .doOnTerminate { showLoader(false) }
                .subscribe({
                    Timber.d("Currencies loaded correctly")
                    currencyExchangerData.value = it
                    startRatesPolling()
                }, {
                    Timber.e("Error during load currencies $it")
                    //TODO fire error
                })
        )
    }

    private fun startRatesPolling() {
        subscription.add(
            Observable.interval(0, 1, TimeUnit.SECONDS)
                .flatMap { ratesRepo.loadCurrencyRatesFromAPI().toObservable() }
                .subscribeOn(Schedulers.io())
                .doOnNext { lastCurrencyList = it }
                .subscribe({
                    Timber.d("polling OK !")
                }, {
                    Timber.e("Error during polling currencies $it")
                })
        )
    }

    private fun buildCurrencyExchangerList(currencyList: ArrayList<CurrencyModel>): ArrayList<CurrencyExchangerModel> {
        val currExcList = ArrayList<CurrencyExchangerModel>()

        for (currencyModel in currencyList) {
            currExcList.add(CurrencyExchangerModel(currencyModel))
        }

        return currExcList
    }

    private fun updateCurrencyExchangerList() {

    }

    private fun showLoader(bool: Boolean) {
        isLoading.value = bool
    }
}