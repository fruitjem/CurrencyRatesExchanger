package fc.home_work.revolut.ui

import android.util.Log
import fc.home_work.revolut.base.BaseViewModel
import fc.home_work.revolut.repository.RatesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RatesViewModel : BaseViewModel() {

    @Inject
    lateinit var ratesRepo: RatesRepository

    init {
        startServicePolling()
    }

    private fun startServicePolling(){

        subscription.add(
            ratesRepo.loadCurrencyRatesFromAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("RatesViewModel","POUND  ${it.rates.gBP}")
                }, {
                    Log.e("RatesViewModel","POUND  ${it.printStackTrace()}")

                })
        )
    }
}