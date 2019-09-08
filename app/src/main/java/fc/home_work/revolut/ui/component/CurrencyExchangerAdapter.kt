package fc.home_work.revolut.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fc.home_work.revolut.R
import fc.home_work.revolut.base.ui.RatesTextWatcher
import fc.home_work.revolut.model.CurrencyExchangerModel
import kotlinx.android.synthetic.main.item_currency_exchanger.view.*

class CurrencyExchangerAdapter(private val clickListener: (model: CurrencyExchangerModel, position:Int) -> Unit,
                               private val onValueChanged:(newValue:String) -> Unit) :
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
            ),
            onValueChanged
        )
    }

    override fun onBindViewHolder(holder: CurrencyExchangerItemViewHolder, position: Int) {
        holder.bind(getItem(position), position, clickListener)
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
        }else{
            // default to full bind:
            super.onBindViewHolder(holder, position, payloads)
        }

    }

}

class CurrencyExchangerItemViewHolder(itemView: View, onValueChange:(newValue:String) -> Unit) : RecyclerView.ViewHolder(itemView) {

    private val textWatcher = RatesTextWatcher { onValueChange(it) }

    fun bind(
        currencyExchangeModel: CurrencyExchangerModel,
        position: Int,
        clickListener: (model: CurrencyExchangerModel, position:Int) -> Unit
    ) {

        itemView.currencyCode.text = currencyExchangeModel.currency.id
        itemView.currencyExchangeValue.setText(currencyExchangeModel.currentValue.toString())
        itemView.currencyFlag.setBackgroundResource(currencyExchangeModel.currencyFlagResourceID)
        itemView.currencyDescription.text = itemView.context.resources.getString(currencyExchangeModel.currencyDescriptionResourceID)

        itemView.currencyExchangeValue.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus)
                itemView.currencyExchangeValue.addTextChangedListener(textWatcher)
            else
                itemView.currencyExchangeValue.removeTextChangedListener(textWatcher)
        }

        //Add clickListener for the other rows
        when(position){
            0 -> {
                itemView.setOnClickListener(null)
                itemView.currencyExchangeValue.isEnabled = true
                itemView.currencyExchangeValue.requestFocus()
            }
            else -> {
                itemView.setOnClickListener { clickListener(currencyExchangeModel, position) }
                itemView.currencyExchangeValue.isEnabled = false

            }
        }
    }

    fun bindJustTheValue(
        newValue: Double,
        position: Int){

        if(position != 0) itemView.currencyExchangeValue.setText(newValue.toString())

    }

}