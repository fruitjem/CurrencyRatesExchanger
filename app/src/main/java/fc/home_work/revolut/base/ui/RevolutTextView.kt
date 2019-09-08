package fc.home_work.revolut.base.ui

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class RevolutTextView : TextView {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }

    private fun init(){
        if (!isInEditMode) {
            val tf = Typeface.createFromAsset(context.assets, "Roboto-Regular.ttf")
            typeface = tf

        }
    }
}