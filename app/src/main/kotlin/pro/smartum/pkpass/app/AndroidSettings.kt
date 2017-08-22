package pro.smartum.pkpass.app

import android.content.Context
import android.preference.PreferenceManager
import pro.smartum.pkpass.R
import pro.smartum.pkpass.model.comparator.PassSortOrder
import java.io.File

class AndroidSettings(val context: Context) : Settings {

    internal val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    override fun getSortOrder(): PassSortOrder {
        val key = context.getString(R.string.preference_key_sort)
        val stringValue = sharedPreferences.getString(key, "0")
        val id = Integer.valueOf(stringValue)!!

        return PassSortOrder.values().first { it.int == id }
    }

    override fun doTraceDroidEmailSend() = true

    override fun getPassesDir() = File(context.filesDir.absolutePath, "passes")

    override fun getStateDir() = File(context.filesDir, "state")

    override fun isCondensedModeEnabled() = false
    //sharedPreferences.getBoolean(context.getString(preference_key_condensed), false)

    override fun isAutomaticLightEnabled() = true
    //sharedPreferences.getBoolean(context.getString(preference_key_autolight), true)

}
