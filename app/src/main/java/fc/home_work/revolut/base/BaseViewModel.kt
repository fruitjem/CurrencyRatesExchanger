package fc.home_work.revolut.base

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    protected var subscription: CompositeDisposable = CompositeDisposable()

    private val errorEvent : MutableLiveData<Int> = MutableLiveData()

    protected fun fireErrorEvent ( @StringRes errorMessage:Int){
        errorEvent.value = errorMessage
    }

    fun getErrorEventObservable(): MutableLiveData<Int> {
        return errorEvent
    }

    override fun onCleared() {
        subscription.clear()
        super.onCleared()
    }
}