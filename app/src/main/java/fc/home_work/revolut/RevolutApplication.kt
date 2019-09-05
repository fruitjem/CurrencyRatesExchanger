package fc.home_work.revolut

import android.app.Application
import timber.log.Timber

class RevolutApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var instance: RevolutApplication
            private set
    }
}