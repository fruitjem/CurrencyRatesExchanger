package fc.home_work.revolut.ui.component

import androidx.recyclerview.widget.DiffUtil
import fc.home_work.revolut.model.CurrencyExchangerModel
import android.os.Bundle
import fc.home_work.revolut.model.CurrencyExchangerModel.Companion.CURRENT_VALUE


class CurrencyExchangerDiffCallback : DiffUtil.ItemCallback<CurrencyExchangerModel>() {

    override fun areItemsTheSame(oldItem: CurrencyExchangerModel, newItem: CurrencyExchangerModel): Boolean {
        return oldItem?.currency.id == newItem?.currency.id
    }

    override fun areContentsTheSame(oldItem: CurrencyExchangerModel, newItem: CurrencyExchangerModel): Boolean {
        return  oldItem?.currentValue == newItem?.currentValue
    }

    override fun getChangePayload(
        oldItem: CurrencyExchangerModel,
        newItem: CurrencyExchangerModel
    ): Any? {
        val bundleDiff = Bundle()

        if(oldItem.currentValue != newItem.currentValue){
            bundleDiff.putDouble (CURRENT_VALUE, newItem.currentValue)
        }

        return if(bundleDiff.size() > 0)
            bundleDiff
        else
            null
    }

}