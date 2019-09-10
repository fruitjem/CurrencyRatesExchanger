package fc.home_work.revolut.network.converter

import fc.home_work.revolut.model.CurrencyModel
import fc.home_work.revolut.network.dto.LatestCurrencyRatesResponseDTO

object RatesConverter {

    fun getRatesListFromDTO(dto:LatestCurrencyRatesResponseDTO,supportedCurrencyArray:Array<String>):ArrayList<CurrencyModel>{

        val result = ArrayList<CurrencyModel>()

        //add the base rate
        result.add(CurrencyModel(dto.base))

        //add the other currencies rates
        for (supportedCurrency in supportedCurrencyArray) {
            val rateValue = dto.rates.getValueByCurrencyID(supportedCurrency)
            if(rateValue != null)
                result.add( CurrencyModel(supportedCurrency,rateValue) )
        }

        return result
    }
}