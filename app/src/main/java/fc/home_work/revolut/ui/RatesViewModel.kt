package fc.home_work.revolut.ui

import androidx.lifecycle.MutableLiveData
import fc.home_work.revolut.base.BaseViewModel
import fc.home_work.revolut.exception.NoDoubleException
import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.repository.RatesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class RatesViewModel : BaseViewModel() {

    @Inject
    lateinit var ratesRepo: RatesRepository

    //Local Data
    private var lastCurrencyExchangerList: ArrayList<CurrencyExchangerModel> = ArrayList()

    //Live Data
    private val currencyExchangerObservableData: MutableLiveData<ArrayList<CurrencyExchangerModel>> = MutableLiveData()

    init {
        loadData()
    }

    fun getCurrencyExchangerObservableList(): MutableLiveData<ArrayList<CurrencyExchangerModel>> {
        return currencyExchangerObservableData
    }

    /**
     * When the user insert a value on EditText this method is invoked in order to refresh the list Values
     */
    fun updateListWithNewValue(model:CurrencyExchangerModel, newValue:String){

        try {
            val newBaseValue = RatesHelper.calculateNewBaseValue(model.currency.currencyExchangeParams,newValue)
            lastCurrencyExchangerList = RatesHelper.updateCurrencyExchangerListWithNewBaseValue(lastCurrencyExchangerList,newBaseValue )
            currencyExchangerObservableData.value = lastCurrencyExchangerList
        }catch (ex:Exception){
            lastCurrencyExchangerList = RatesHelper.updateCurrencyExchangerListWithNewBaseValue(lastCurrencyExchangerList,0.0 )
        }

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
                .doOnNext{
                    lastCurrencyExchangerList = it
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currencyExchangerObservableData.value = lastCurrencyExchangerList
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Polling Rates OK, saved items on DB")
                }, {
                    Timber.e("Error during polling currencies $it")
                })
        )
    }

    private fun updateCurrencyExchangerList(updatedCurrencyList:ArrayList<CurrencyModel>): ArrayList<CurrencyExchangerModel> {
        return if(lastCurrencyExchangerList.isEmpty()){
            Timber.d("Currency Exchange List CREATED")
            RatesHelper.buildCurrencyExchangerList(updatedCurrencyList)
        } else{
            Timber.d("Currency Exchange List UPDATED")
            RatesHelper.updateCurrencyExchangerListWithNewRates(updatedCurrencyList,lastCurrencyExchangerList )
        }
    }
}