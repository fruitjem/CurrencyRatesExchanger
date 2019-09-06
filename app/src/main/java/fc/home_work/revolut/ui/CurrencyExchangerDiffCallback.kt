package fc.home_work.revolut.ui

import androidx.recyclerview.widget.DiffUtil
import fc.home_work.revolut.model.CurrencyExchangerModel

class CurrencyExchangerDiffCallback : DiffUtil.ItemCallback<CurrencyExchangerModel>() {

    override fun areItemsTheSame(oldItem: CurrencyExchangerModel, newItem: CurrencyExchangerModel): Boolean {
        return oldItem?.currency.id == newItem?.currency.id
    }

    override fun areContentsTheSame(oldItem: CurrencyExchangerModel, newItem: CurrencyExchangerModel): Boolean {
        return  oldItem?.currentValue == newItem?.currentValue
    }

}