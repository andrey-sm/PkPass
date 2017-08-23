package pro.smartum.pkpass.ui.adapter.holder

import android.app.Activity
import android.support.v7.widget.CardView
import android.view.View
import kotlinx.android.synthetic.main.item_pass.view.*
import pro.smartum.pkpass.model.pass.Pass
import pro.smartum.pkpass.storage.PassStore

open class VerbosePassViewHolder(view: CardView) : PassViewHolder(view) {

    override fun apply(pass: Pass, passStore: PassStore, activity: Activity) {
        super.apply(pass, passStore, activity)

        val stringToSetForDateOrExtraText = getTimeInfoString(pass) ?: getExtraString(pass)

        if (stringToSetForDateOrExtraText != null && !stringToSetForDateOrExtraText.isEmpty()) {
            view.date.text = stringToSetForDateOrExtraText
            view.date.visibility = View.VISIBLE
        } else {
            view.date.visibility = View.GONE
        }
    }
}
