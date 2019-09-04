package fc.home_work.revolut

import android.app.Application

class RevolutApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: RevolutApplication
            private set
    }
}