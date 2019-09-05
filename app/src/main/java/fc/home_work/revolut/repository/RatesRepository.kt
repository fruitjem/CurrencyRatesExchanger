package fc.home_work.revolut.repository

import android.content.Context
import fc.home_work.revolut.R
import fc.home_work.revolut.base.BaseRepository
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.network.RatesAPI
import fc.home_work.revolut.network.converter.RatesConverter
import io.reactivex.Single
import javax.inject.Inject

class RatesRepository(context:Context) : BaseRepository(context){
    @Inject
    lateinit var ratesAPI: RatesAPI

    /**
     * load the updated chatList from API
     */
    fun loadCurrencyRatesFromAPI(): Single<ArrayList<CurrencyModel>> {
        val supportedCurrency = context.resources.getStringArray(R.array.currencies)
        return ratesAPI.getLatestRates(EUR_CURRENCY_ID).map { RatesConverter.getRatesListFromDTO(it,supportedCurrency) }
    }

    companion object {
        const val EUR_CURRENCY_ID = "EUR" //BASE
    }
}