package fc.home_work.revolut.repository

import android.content.Context
import fc.home_work.revolut.R
import fc.home_work.revolut.base.BaseRepository
import fc.home_work.revolut.database.RevolutDatabase
import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.network.RatesAPI
import fc.home_work.revolut.network.converter.RatesConverter
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RatesRepository(context:Context) : BaseRepository(context){

    @Inject
    lateinit var ratesAPI: RatesAPI

    @Inject
    lateinit var db: RevolutDatabase

    /**
     * load the updated chatList from API
     */
    fun loadCurrencyRatesFromAPI(): Single<ArrayList<CurrencyModel>> {
        val supportedCurrency = context.resources.getStringArray(R.array.currencies)
        return ratesAPI.getLatestRates(EUR_CURRENCY_ID).map { RatesConverter.getRatesListFromDTO(it,supportedCurrency) }
    }

    /**
     * save rates on DB
     */
    fun saveCurrencyRatesOnDB(list:ArrayList<CurrencyModel>): Completable {
        return db.currencyRatesDAO().insertAll(list)
    }

    /**
     * get flawable rates from DB
     */
    fun getFlowableRates(): Flowable<List<CurrencyModel>> {
        return db.currencyRatesDAO().getAll()
    }

    companion object {
        const val EUR_CURRENCY_ID = "EUR" //BASE
    }
}