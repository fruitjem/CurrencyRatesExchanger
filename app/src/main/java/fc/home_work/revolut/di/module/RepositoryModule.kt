package fc.home_work.revolut.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import fc.home_work.revolut.repository.RatesRepository


/**
 * Module which provides all required dependencies about repository
 */
@Module
object RepositoryModule {

    /**
     * Provides the RatesRepository obj
     * @param context
     * @return the RatesRepository implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRatesRepository(context: Context): RatesRepository {
        return RatesRepository(context)
    }





}