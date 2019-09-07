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
    private var lastCurrencyExchangerList: ArrayList<CurrencyExchangerModel> = ArrayList()

    //Live Data
    private val currencyExchangerData: MutableLiveData<ArrayList<CurrencyExchangerModel>> = MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        loadData()
    }

    fun getCurrencyExchangerObservableList(): MutableLiveData<ArrayList<CurrencyExchangerModel>> {
        return currencyExchangerData
    }

    fun getLoadingObservable(): MutableLiveData<Boolean> {
        return isLoading
    }

    private fun loadData() {
        observeCurrencyRatesChanges()
        startRatesPolling()
    }


    private fun observeCurrencyRatesChanges(){
        subscription.add(
                ratesRepo.getFlowableRates()
                .subscribeOn(Schedulers.io())
                .map {
                    updateCurrencyExchangerList(it as ArrayList<CurrencyModel>)
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Currencies Flowable update DONE ${it[0].currency.currencyExchangeParams}")
                    lastCurrencyExchangerList = it
                    currencyExchangerData.value = lastCurrencyExchangerList
                    it.forEach{ it.toString() }
                }, {
                    Timber.e("Error during load currencies $it")
                })
        )
    }

    private fun startRatesPolling() {
        subscription.add(
            Observable.interval(0, 1, TimeUnit.SECONDS)
                .flatMap { ratesRepo.loadCurrencyRatesFromAPI().toObservable() }
                .flatMapCompletable { ratesRepo.saveCurrencyRatesOnDB(it) }
                .subscribeOn(Schedulers.io())
                //.map { RatesHelper.updateCurrencyExchangerList(it,lastCurrencyExchangerList) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Polling Rates OK, saved items on DB")
                }, {
                    Timber.e("Error during polling currencies $it")
                })
        )
    }

    private fun updateCurrencyExchangerList(updatedCurrencyList:ArrayList<CurrencyModel>): ArrayList<CurrencyExchangerModel> {
        if(lastCurrencyExchangerList.isEmpty()){
            Timber.d("Currency Exchange List CREATED")
            return RatesHelper.buildCurrencyExchangerList(updatedCurrencyList)
        }
        else{
            Timber.d("Currency Exchange List UPDATED")
            return RatesHelper.updateCurrencyExchangerList(updatedCurrencyList,lastCurrencyExchangerList )
        }
    }

    private fun showLoader(bool: Boolean) {
        isLoading.value = bool
    }
}