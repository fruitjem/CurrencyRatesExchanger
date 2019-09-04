package fc.home_work.revolut.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import fc.home_work.revolut.database.RevolutDatabase


/**
 * Module which provides all required dependencies about database
 */
@Module
object DatabaseModule {
    /**
     * Provides the Room Database object.
     * @return the InfiniteDatabase object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideDatabase(context: Context): RevolutDatabase {
        return RevolutDatabase.getAppDatabase(context)!!
    }
}