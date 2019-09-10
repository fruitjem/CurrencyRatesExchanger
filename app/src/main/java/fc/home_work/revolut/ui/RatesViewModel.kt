package fc.home_work.revolut.ui

import androidx.lifecycle.MutableLiveData
import fc.home_work.revolut.R
import fc.home_work.revolut.base.BaseViewModel
import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.repository.RatesRepository
import fc.home_work.revolut.ui.helper.CurrencyHelper
import fc.home_work.revolut.ui.helper.CurrencyExchagerHelper
import fc.home_work.revolut.util.RATES_POLLING
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

open class RatesViewModel(private val ratesRepo: RatesRepository) : BaseViewModel() {

    //Local Data
    private var lastCurrencyExchangerList: ArrayList<CurrencyExchangerModel> = ArrayList()

    //Live Data
    private val currencyExchangerObservableData: MutableLiveData<Pair<ArrayList<CurrencyExchangerModel>,Boolean>> =
        MutableLiveData()

    fun loadData() {
        observeCurrencyRatesChanges()
        startRatesPolling()
    }

    fun stopPolling(){
        subscription.clear()
    }

    fun getCurrencyExchangerObservableList(): MutableLiveData<Pair<ArrayList<CurrencyExchangerModel>, Boolean>> {
        return currencyExchangerObservableData
    }

    /**
     * When the user insert a value on EditText this method is invoked in order to refresh the list Values
     */
    fun updateListWithNewValue(newValue: String) {

        lastCurrencyExchangerList =
            try {

                val newBaseValue = CurrencyHelper.calculateNewBaseValue(
                    lastCurrencyExchangerList[0].currency.currencyExchangeParams,
                    newValue
                )
                CurrencyExchagerHelper.updateCurrencyExchangerListWithNewBaseValue(lastCurrencyExchangerList, newBaseValue)

            } catch (ex: Exception) {
                CurrencyExchagerHelper.updateCurrencyExchangerListWithNewBaseValue(lastCurrencyExchangerList, 0.0)
            } finally {
                currencyExchangerObservableData.value = Pair(lastCurrencyExchangerList,false)
            }
    }

    fun swapCurrencyExchangerToTopPosition(position: Int) {
        lastCurrencyExchangerList = CurrencyExchagerHelper.moveElementInFirstPosition(lastCurrencyExchangerList, position)
        currencyExchangerObservableData.value = Pair(lastCurrencyExchangerList,true)
    }

    /**
     * Listen from DB source and update the CurrencyExchangeModel with last Rates available
     */
    fun observeCurrencyRatesChanges() {
        subscription.add(
            ratesRepo.getFlowableRates()
                .subscribeOn(Schedulers.io())
                .map {
                    updateCurrencyExchangerListWithNewRates(it as ArrayList<CurrencyModel>)
                }
                .doOnNext {
                    lastCurrencyExchangerList = it
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currencyExchangerObservableData.value = Pair(lastCurrencyExchangerList,false) //Update Live DATA
                }, {
                    Timber.e("Error during load currencies $it")
                })
        )
    }

    /**
     * Polling the Rates service and save new CurrencyModels on DB
     */
    private fun startRatesPolling() {

        subscription.add(
            Observable.interval(0, RATES_POLLING, TimeUnit.SECONDS)
                .flatMap { ratesRepo.loadCurrencyRatesFromAPI().toObservable() }
                .flatMapCompletable { ratesRepo.saveCurrencyRatesOnDB(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Polling Rates OK, saved items on DB")
                }, {
                    Timber.e("Error during polling currencies $it")
                    checkIfDatabaseIsEmptyAndShowAlert()
                })
        )
    }

    fun checkIfDatabaseIsEmptyAndShowAlert(){
        subscription.add(
                ratesRepo.checkIfRatesDBTableIsEmpty()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isEmpty ->
                    if(isEmpty)
                        fireErrorEvent(R.string.user_is_offline_error)
                    else
                        fireErrorEvent(R.string.user_is_offline_warning)
                },{
                    Timber.e("Something went wrong during database check $it")
                })
        )
    }

    private fun updateCurrencyExchangerListWithNewRates(updatedCurrencyList: ArrayList<CurrencyModel>): ArrayList<CurrencyExchangerModel> {
        return if (lastCurrencyExchangerList.isEmpty()) {
            Timber.d("Currency Exchange List CREATED")
            CurrencyExchagerHelper.buildCurrencyExchangerList(updatedCurrencyList)
        } else {
            Timber.d("Currency Exchange List UPDATED")
            CurrencyExchagerHelper.updateCurrencyExchangerListWithNewRates(
                updatedCurrencyList,
                lastCurrencyExchangerList
            )
        }
    }
}