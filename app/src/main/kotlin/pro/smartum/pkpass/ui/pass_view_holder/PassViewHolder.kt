package pro.smartum.pkpass.ui.pass_view_holder

import android.app.Activity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View.*
import kotlinx.android.synthetic.main.pass_list_item.view.*
import kotlinx.android.synthetic.main.time_and_nav.view.*
import org.threeten.bp.ZonedDateTime
import pro.smartum.pkpass.R
import pro.smartum.pkpass.model.PassBitmapDefinitions
import pro.smartum.pkpass.model.pass.Pass
import pro.smartum.pkpass.model.pass.PassField
import pro.smartum.pkpass.storage.PassStore

abstract class PassViewHolder(val view: CardView) : RecyclerView.ViewHolder(view) {

    open fun apply(pass: Pass, passStore: PassStore, activity: Activity) {
        setupButtons(activity, pass)

        refresh(pass, passStore)
    }

    open fun setupButtons(activity: Activity, pass: Pass) {
        view.timeAndNavBar.timeButton.text = view.context.getString(R.string.pass_to_calendar)
        view.timeAndNavBar.locationButton.text = view.context.getString(R.string.pass_directions)

//        view.timeAndNavBar.timeButton.setOnClickListener {
//            getDateOrExtraText(pass)?.let { tryAddDateToCalendar(pass, view, it) }
//        }
//
//        view.timeAndNavBar.locationButton.setOnClickListener {
//            activity.showNavigateToLocationsDialog(pass, false)
//        }
    }

    protected fun refresh(pass: Pass, passStore: PassStore) {
        val dateOrExtraText = getDateOrExtraText(pass)

        val noButtons = dateOrExtraText == null && pass.locations.isEmpty()

        view.actionsSeparator.visibility = getVisibilityForGlobalAndLocal(noButtons, true)
        view.timeAndNavBar.locationButton.visibility = getVisibilityForGlobalAndLocal(noButtons, pass.locations.isNotEmpty())

        view.timeAndNavBar.timeButton.visibility = getVisibilityForGlobalAndLocal(noButtons, dateOrExtraText != null)

        val iconBitmap = pass.getBitmap(passStore, PassBitmapDefinitions.BITMAP_ICON)

        iconBitmap?.let { view.categoryView.setIcon(it) }

        view.categoryView.setImageByCategory(pass.type)

        view.categoryView.setAccentColor(pass.accentColor)

        view.passTitle.text = pass.description
    }

    fun getExtraString(pass: Pass) = pass.fields.firstOrNull()?.let { getExtraStringForField(it) }


    private fun getExtraStringForField(passField: PassField): String {
        val stringBuilder = StringBuilder()

        if (passField.label != null) {
            stringBuilder.append(passField.label)

            if (passField.value != null) {
                stringBuilder.append(": ")
            }
        }

        if (passField.value != null) {
            stringBuilder.append(passField.value)
        }

        return stringBuilder.toString()
    }

    private fun setDateTextFromDateAndPrefix(prefix: String, relevantDate: ZonedDateTime): String {
        val relativeDateTimeString = DateUtils.getRelativeDateTimeString(view.context,
                relevantDate.toEpochSecond() * 1000,
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,
                0)

        return prefix + relativeDateTimeString
    }

    protected fun getTimeInfoString(pass: Pass) = when {
        pass.calendarTimespan?.from != null -> setDateTextFromDateAndPrefix("", pass.calendarTimespan!!.from!!)

        pass.validTimespans.orEmpty().isNotEmpty() && pass.validTimespans!![0].to != null -> {
            val to = pass.validTimespans!![0].to
            setDateTextFromDateAndPrefix(if (to!!.isAfter(ZonedDateTime.now())) "expires " else " expired ", to)
        }
        else -> null
    }

    private fun getDateOrExtraText(pass: Pass) = when {
        pass.calendarTimespan != null -> pass.calendarTimespan
        pass.validTimespans.orEmpty().isNotEmpty() -> pass.validTimespans!![0]
        else -> null
    }

    /////////////@Visibility
    protected open fun getVisibilityForGlobalAndLocal(global: Boolean, local: Boolean) = when {
        global -> GONE
        local -> VISIBLE
        else -> INVISIBLE
    }

}
