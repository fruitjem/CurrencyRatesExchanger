package infinite_software.intelligence_center.revolut.ui

import android.os.Bundle
import android.os.PersistableBundle
import infinite_software.intelligence_center.revolut.R
import infinite_software.intelligence_center.revolut.base.BaseActivity

class RatesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_rates)
    }

}