package fc.home_work.revolut.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fc.home_work.revolut.R
import fc.home_work.revolut.model.CurrencyExchangerModel
import kotlinx.android.synthetic.main.item_currency_exchanger.view.*

class CurrencyExchangerAdapter(private val clickListener: (model:CurrencyExchangerModel) -> Unit): ListAdapter<CurrencyExchangerModel, CurrencyExchangerItemViewHolder >(CurrencyExchangerDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyExchangerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CurrencyExchangerItemViewHolder(inflater.inflate(R.layout.item_currency_exchanger, parent, false))
    }

    override fun onBindViewHolder(holder: CurrencyExchangerItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

class CurrencyExchangerItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(currencyExchangeModel:CurrencyExchangerModel, clickListener: (model:CurrencyExchangerModel) -> Unit){
        itemView.currencyValue.text = currencyExchangeModel.currentValue.toString()
    }

}