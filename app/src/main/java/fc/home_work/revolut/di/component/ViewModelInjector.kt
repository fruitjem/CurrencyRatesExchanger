package fc.home_work.revolut.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import fc.home_work.revolut.di.module.RepositoryModule
import fc.home_work.revolut.ui.RatesViewModel
import javax.inject.Singleton

/**
 * Component providing inject() methods for ViewModels.
 */
@Singleton
@Component(modules = [(RepositoryModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified Presenter.
     */

    fun inject(ratesViewModel: RatesViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
        @BindsInstance //for add component that are created outside the graph
        fun context(context: Context): Builder
    }

}