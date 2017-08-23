package pro.smartum.pkpass.util

import android.util.Log
import pro.smartum.pkpass.BuildConfig

object Logger {
    private val APP_TAG = BuildConfig.APPLICATION_ID

    fun e(clazz: Class<*>, tr: Throwable) {
        e(clazz, clazz.simpleName + " : " + tr.message, tr)
    }

    /** System calls  */
    @JvmOverloads fun e(clazz: Class<*>, msg: String, th: Throwable) {
        if (isLoggable) {
            Log.e(APP_TAG, clazz.simpleName + " : " + msg, th)
            log(clazz, msg, th)
        }
    }

    fun e(clazz: Class<*>, msg: String) {
        if (isLoggable)
            Log.d(APP_TAG, clazz.simpleName + " : " + msg)
    }

    @JvmOverloads fun w(clazz: Class<*>, msg: String, tr: Throwable) {
        if (isLoggable)
            Log.w(APP_TAG, clazz.simpleName + " : " + msg, tr)
    }

    fun i(clazz: Class<*>, msg: String) {
        if (isLoggable)
            Log.i(APP_TAG, clazz.simpleName + " : " + msg)
    }

    fun d(clazz: Class<*>, msg: String) {
        if (isLoggable)
            Log.d(APP_TAG, clazz.simpleName + " : " + msg)
    }

    fun log(clazz: Class<*>, msg: String, th: Throwable) {
        Log.i(APP_TAG, clazz.simpleName + " : " + msg)
        th.printStackTrace()
    }

    val isLoggable: Boolean
        get() = true
}
