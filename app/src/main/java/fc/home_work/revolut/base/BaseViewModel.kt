package fc.home_work.revolut.base

import androidx.lifecycle.ViewModel
import fc.home_work.revolut.RevolutApplication
import fc.home_work.revolut.di.component.DaggerViewModelInjector
import fc.home_work.revolut.di.component.ViewModelInjector
import fc.home_work.revolut.di.module.RepositoryModule
import fc.home_work.revolut.ui.RatesViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected var subscription: CompositeDisposable = CompositeDisposable()

    init {
        inject()
    }

    private val injector : ViewModelInjector
        get() = DaggerViewModelInjector.builder()
            .repositoryModule(RepositoryModule)
            .context(RevolutApplication.instance.applicationContext)
            .build()

    private fun inject(){
        when (this) {
            is RatesViewModel -> injector.inject(this)
        }
    }

}