package pro.smartum.pkpass.app

import pro.smartum.pkpass.model.comparator.PassSortOrder
import java.io.File

interface Settings {

    fun getSortOrder(): PassSortOrder

    fun doTraceDroidEmailSend(): Boolean

    fun getPassesDir(): File

    fun getStateDir(): File

    fun isCondensedModeEnabled(): Boolean

    fun isAutomaticLightEnabled(): Boolean

//    fun getNightMode(): Int
}
