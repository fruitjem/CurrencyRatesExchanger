package fc.home_work.revolut.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected var subscription: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        subscription.clear()
        super.onCleared()
    }

}