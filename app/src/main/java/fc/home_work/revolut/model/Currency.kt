package fc.home_work.revolut.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CurrencyState")
class Currency{

    @PrimaryKey(autoGenerate = false)
    var id: String

    @ColumnInfo(name = "currencyExchangeParams")
    var currencyExchangeParams: Double

    var currentValue : Double = 0.0


    constructor(id:String, currencyExchangeParams:Double = 1.0){
        this.id = id
        this.currencyExchangeParams = currencyExchangeParams
    }

}