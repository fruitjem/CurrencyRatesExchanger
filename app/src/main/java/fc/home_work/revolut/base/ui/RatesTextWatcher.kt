package fc.home_work.revolut.base.ui

import android.text.Editable
import android.text.TextWatcher

class RatesTextWatcher(private val afterTextChanged: (String) -> Unit) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged.invoke(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}