package fc.home_work.revolut.network

import fc.home_work.revolut.network.dto.LatestCurrencyRatesResponseDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesAPI {

    @GET("https://revolut.duckdns.org/latest")
    fun getLatestRates(@Query("base") baseCurrency:String) : Single<LatestCurrencyRatesResponseDTO>

}