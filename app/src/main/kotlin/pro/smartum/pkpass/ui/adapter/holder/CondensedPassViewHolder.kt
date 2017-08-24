package pro.smartum.pkpass.ui.adapter.holder

import android.app.Activity
import android.support.v7.widget.CardView
import android.view.View
import kotlinx.android.synthetic.main.item_pass.view.*
import kotlinx.android.synthetic.main.time_and_nav.view.*
import pro.smartum.pkpass.model.pass.Pass
import pro.smartum.pkpass.storage.PassStore

class CondensedPassViewHolder(view: CardView) : PassViewHolder(view) {

    override fun apply(pass: Pass, passStore: PassStore, activity: Activity) {
        super.apply(pass, passStore, activity)

        val extraString = getExtraString(pass)

        if (extraString.isNullOrBlank()) {
            view.vDate.visibility = View.GONE
        } else {
            view.vDate.text = extraString
            view.vDate.visibility = View.VISIBLE
        }

        view.vTimeAndNavBar.vTimeButton.text = getTimeInfoString(pass)

    }
}
