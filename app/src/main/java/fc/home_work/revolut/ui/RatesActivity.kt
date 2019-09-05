package fc.home_work.revolut.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import fc.home_work.revolut.R
import fc.home_work.revolut.util.getViewModel

class RatesActivity : AppCompatActivity() {

    private lateinit var viewModel : RatesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)
        viewModel = getViewModel()

        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.getCurrencyExchangerObservableList().observe(this, Observer {
            updateList()
        })
    }

    private fun updateList(){

    }

}