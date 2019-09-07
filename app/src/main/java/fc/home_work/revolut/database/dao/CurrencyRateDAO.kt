package fc.home_work.revolut.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fc.home_work.revolut.model.CurrencyModel
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CurrencyRateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ratesList: List<CurrencyModel>) :Completable

    @Query("SELECT * FROM CurrencyState")
    fun getAll(): Flowable<List<CurrencyModel>>
}