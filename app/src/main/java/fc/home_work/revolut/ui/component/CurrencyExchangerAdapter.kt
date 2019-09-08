package fc.home_work.revolut.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fc.home_work.revolut.R
import fc.home_work.revolut.model.CurrencyExchangerModel
import fc.home_work.revolut.util.afterTextChanged
import kotlinx.android.synthetic.main.item_currency_exchanger.view.*

class CurrencyExchangerAdapter(private val clickListener: (model: CurrencyExchangerModel, position:Int) -> Unit,
                               private val onValueChanged:(model: CurrencyExchangerModel, newValue:String) -> Unit) :
    ListAdapter<CurrencyExchangerModel, CurrencyExchangerItemViewHolder>(
        CurrencyExchangerDiffCallback()
    ){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyExchangerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CurrencyExchangerItemViewHolder(
            inflater.inflate(
                R.layout.item_currency_exchanger,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyExchangerItemViewHolder, position: Int) {
        holder.bind(getItem(position), position, clickListener, onValueChanged)
    }

    override fun onBindViewHolder(
        holder: CurrencyExchangerItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (!payloads.isNullOrEmpty()) {
            val bundle = payloads[0] as Bundle
            val newValue = bundle.getDouble(CurrencyExchangerModel.CURRENT_VALUE)
            holder.bindJustTheValue(newValue,position)
        }

        // default to full bind:
        super.onBindViewHolder(holder, position, payloads)
    }
}

class CurrencyExchangerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        currencyExchangeModel: CurrencyExchangerModel,
        position: Int,
        clickListener: (model: CurrencyExchangerModel, position:Int) -> Unit,
        onValueChanged:(model: CurrencyExchangerModel, newValue:String) -> Unit
    ) {

        itemView.currencyCode.text = currencyExchangeModel.currency.id
        itemView.currencyExchangeValue.setText(currencyExchangeModel.currentValue.toString())
        itemView.currencyFlag.setBackgroundResource(currencyExchangeModel.currencyFlagResourceID)
        itemView.currencyDescription.text = itemView.context.resources.getString(currencyExchangeModel.currencyDescriptionResourceID)

        //Enable EditText only for the first item
        itemView.currencyExchangeValue.isEnabled = (position == 0)

        //Add clickListener for the other rows
        when(position){
            0 -> {
                itemView.setOnClickListener(null)
                itemView.currencyExchangeValue.afterTextChanged { newValue -> onValueChanged(currencyExchangeModel,newValue ) }
            }
            else -> {
                itemView.setOnClickListener { clickListener(currencyExchangeModel, position) }
            }
        }
    }


    fun bindJustTheValue(
        newValue: Double,
        position: Int){

        if(position != 0) itemView.currencyExchangeValue.setText(newValue.toString())

    }

}