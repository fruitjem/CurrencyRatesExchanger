package fc.home_work.revolut.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fc.home_work.revolut.R
import fc.home_work.revolut.base.BaseActivity
import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.ui.component.CurrencyExchangerAdapter
import fc.home_work.revolut.util.getViewModel
import kotlinx.android.synthetic.main.activity_rates.*
import timber.log.Timber

class RatesActivity : BaseActivity() {

    private lateinit var viewModel : RatesViewModel
    private var currencyExchangerAdapter : CurrencyExchangerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)
        viewModel = getViewModel()
        initViews { observeViewModel() }
    }

    private fun initViews(onViewsInitComplete: () -> Unit){

        //init RV
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        currenciesRV.layoutManager = layoutManager
        currencyExchangerAdapter = CurrencyExchangerAdapter (
            {  model, position -> onCurrencyCLicked(model,position) },
            {  model,value -> onNewBaseValueToCalculate(model,value)}
        )
        currenciesRV.adapter = currencyExchangerAdapter

        onViewsInitComplete()
    }

    private fun onCurrencyCLicked(currencyClicked:CurrencyExchangerModel, position:Int){
        Timber.d("Currency clicked ${currencyClicked.currency.id} at position $position")
    }

    private fun onNewBaseValueToCalculate(currencyExcChanged:CurrencyExchangerModel, newValue:String){
        viewModel.updateListWithNewValue( currencyExcChanged,newValue )
    }

    private fun observeViewModel(){

        viewModel.getCurrencyExchangerObservableList().observe(this, Observer {
            updateList(it)
        })

    }

    private fun updateList(currencyExchangerList:ArrayList<CurrencyExchangerModel>){
        currencyExchangerAdapter?.submitList(
            currencyExchangerList.mapTo(ArrayList()){ it.copy() }
        ) 
    }

}