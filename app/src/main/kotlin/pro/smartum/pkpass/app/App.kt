package pro.smartum.pkpass.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.moshi.Moshi
import pro.smartum.pkpass.model.adapter.ColorAdapter
import pro.smartum.pkpass.model.adapter.ZonedTimeAdapter
import pro.smartum.pkpass.storage.AndroidFileSystemPassStore
import pro.smartum.pkpass.storage.PassStore

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        settings = AndroidSettings(this)

        val moshi = Moshi.Builder()
                .add(ZonedTimeAdapter())
                .add(ColorAdapter())
                .build()
        passStore = AndroidFileSystemPassStore(this, settings, moshi)

        AndroidThreeTen.init(this)
    }

    companion object {
        lateinit var passStore: PassStore
        lateinit var settings: Settings
    }
}
