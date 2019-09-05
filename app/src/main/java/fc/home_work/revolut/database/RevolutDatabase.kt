package fc.home_work.revolut.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fc.home_work.revolut.model.CurrencyModel


@Database(entities = [CurrencyModel::class],version = 1)
abstract class RevolutDatabase : RoomDatabase() {

    companion object {
        var INSTANCE: RevolutDatabase? = null

        fun getAppDatabase(context: Context): RevolutDatabase? {
            if (INSTANCE == null){
                synchronized(RevolutDatabase::class){
                    INSTANCE = Room.databaseBuilder(context , RevolutDatabase::class.java, "revolut_db").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}