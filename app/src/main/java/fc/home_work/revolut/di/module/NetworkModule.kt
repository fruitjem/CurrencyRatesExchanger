package fc.home_work.revolut.di.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import fc.home_work.revolut.BuildConfig
import fc.home_work.revolut.network.RatesAPI
import fc.home_work.revolut.util.CONNECTION_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Module which provides all required dependencies about network
 */
@Module

object NetworkModule {

    /**
     * Provides the Rates service implementation for Currency Rates
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRatesAPI(retrofit: Retrofit): RatesAPI {
        return retrofit.create(RatesAPI::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.interceptors().add(logging)

        //Timeout settings
        httpClient.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)

        //Build
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .baseUrl(BuildConfig.BASE_RATES_URL)
            .build()
    }

}