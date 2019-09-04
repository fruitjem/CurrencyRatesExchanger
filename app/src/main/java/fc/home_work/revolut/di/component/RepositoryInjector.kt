package fc.home_work.revolut.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import fc.home_work.revolut.di.module.DatabaseModule
import fc.home_work.revolut.di.module.NetworkModule
import fc.home_work.revolut.repository.RatesRepository
import javax.inject.Singleton


/**
 * Component providing inject() methods for repository class.
 */
@Singleton
@Component(modules = [(NetworkModule::class),(DatabaseModule::class)])
interface RepositoryInjector {
    /**
     * Injects required dependencies into the specified Repository.
     */

    fun inject(ratesRepository: RatesRepository)

    @Component.Builder
    interface Builder {
        fun build(): RepositoryInjector
        fun networkModule(networkModule: NetworkModule): Builder
        fun databaseModule(dataBaseModule: DatabaseModule): Builder

        @BindsInstance //for add component that are created outside the graph
        fun context(context: Context): Builder
    }
}