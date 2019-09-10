package fc.home_work.revolut.base

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import fc.home_work.revolut.R

abstract class BaseActivity : AppCompatActivity(){

    private lateinit var errorView: View

    protected abstract fun instantiateErrorView(): View

    fun showError(error: String) {
        if (!::errorView.isInitialized) {
            errorView = instantiateErrorView()
        }

        val snackbar = Snackbar.make(
            errorView, error,
            Snackbar.LENGTH_LONG
        )

        snackbar.setActionTextColor(ContextCompat.getColor(this, (R.color.colorAccent)))

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))

        val textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.revolutWhite))
        textView.textSize = 14f
        snackbar.show()
    }

    fun observeErrors(baseViewModel: BaseViewModel) {
        baseViewModel.getErrorEventObservable().observe(this, Observer {
            showError(resources.getString(it))
        })
    }

}