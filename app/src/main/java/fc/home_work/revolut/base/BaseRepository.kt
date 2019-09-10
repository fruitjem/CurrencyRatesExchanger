package fc.home_work.revolut.base

import android.content.Context
import fc.home_work.revolut.di.component.DaggerRepositoryInjector
import fc.home_work.revolut.di.component.RepositoryInjector
import fc.home_work.revolut.di.module.DatabaseModule
import fc.home_work.revolut.di.module.NetworkModule
import fc.home_work.revolut.repository.RatesRepository
import io.reactivex.disposables.CompositeDisposable

abstract class BaseRepository (protected val context: Context) {
    /**
     * Dagger injector
     */
    private val injector: RepositoryInjector
        get() =  DaggerRepositoryInjector
            .builder()
            .context(context)
            .networkModule(NetworkModule)
            .databaseModule(DatabaseModule)
            .build()

    init {
        inject()
    }

    /**
     * Inject modules class
     */
    private fun inject() {
        when (this) {
            is RatesRepository -> injector.inject(this)
        }
    }

}