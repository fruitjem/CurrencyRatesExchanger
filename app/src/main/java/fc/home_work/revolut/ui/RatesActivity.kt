package fc.home_work.revolut.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fc.home_work.revolut.R
import fc.home_work.revolut.base.BaseActivity
import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.repository.RatesRepository
import fc.home_work.revolut.ui.component.CurrencyExchangerAdapter
import fc.home_work.revolut.util.getViewModel
import kotlinx.android.synthetic.main.activity_rates.*
import timber.log.Timber

class RatesActivity : BaseActivity() {

    private lateinit var viewModel : RatesViewModel
    private lateinit var currencyExchangerAdapter : CurrencyExchangerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)

        viewModel = getViewModel{RatesViewModel(RatesRepository(this))}

        initViews { observeViewModel() }
    }

    private fun initViews(onViewsInitComplete: () -> Unit){

        //init RV
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        currenciesRV.layoutManager = layoutManager
        currencyExchangerAdapter = CurrencyExchangerAdapter (
            {  model, position -> onCurrencyCLicked(model,position) },
            {  value -> onNewBaseValueToCalculate(value)}
        )

        currenciesRV.adapter = currencyExchangerAdapter

        onViewsInitComplete()
    }

    private fun observeViewModel(){

        viewModel.getCurrencyExchangerObservableList().observe(this, Observer {
            if(it.second)
                updateListWithReBind(it.first) //if elements position is changed rebind
            else
                updateList(it.first) //changed just the value to show
        })
    }

    private fun onCurrencyCLicked(currencyClicked:CurrencyExchangerModel, position:Int){
        Timber.d("Currency clicked ${currencyClicked.currency.id} at position $position")
        viewModel.swapCurrencyExchangerToTopPosition(position)
    }

    private fun onNewBaseValueToCalculate(newValue:String){
        viewModel.updateListWithNewValue( newValue )
    }

    private fun updateList(currencyExchangerList:ArrayList<CurrencyExchangerModel>){
        currencyExchangerAdapter?.submitList(
            currencyExchangerList.mapTo(ArrayList()){ it.copy() }
        )
    }

    private fun updateListWithReBind(currencyExchangerList:ArrayList<CurrencyExchangerModel>){
        currencyExchangerAdapter?.submitList(
            currencyExchangerList.mapTo(ArrayList()){ it.copy() }
        ) {
            for(i in 0 until currencyExchangerList.size){
                currencyExchangerAdapter?.notifyItemChanged(i)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopPolling()

    }

}