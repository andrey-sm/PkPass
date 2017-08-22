package pro.smartum.pkpass.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.moshi.Moshi
import pro.smartum.pkpass.ColorAdapter
import pro.smartum.pkpass.ZonedTimeAdapter
import pro.smartum.pkpass.storage.AndroidFileSystemPassStore
import pro.smartum.pkpass.storage.PassStore

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

       settings = AndroidSettings(this)

        val build = Moshi.Builder()
                .add(ZonedTimeAdapter())
                .add(ColorAdapter())
                .build()
        passStore = AndroidFileSystemPassStore(this, settings, build)
//        kodein = Kodein {
//            import(createTrackerKodeinModule(this@App))
//            import(createKodein(), allowOverride = true)
//        }

        /////AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        /////////////installLeakCanary()
        AndroidThreeTen.init(this)
        ///////////initTraceDroid()

        ////val settings: Settings = kodein.instance()
        ///////////////////AppCompatDelegate.setDefaultNightMode(settings.getNightMode())
    }

//    open fun createKodein() = Kodein.Module {
//        val build = Moshi.Builder()
//                .add(ZonedTimeAdapter())
//                .add(ColorAdapter())
//                .build()
//
//
//        bind<PassStore>() with singleton { AndroidFileSystemPassStore(this@App, instance(), build, instance()) }
//        bind<Settings>() with singleton { AndroidSettings(this@App) }
//        bind<EventBus>() with singleton { EventBus.getDefault() }
//    }

//    open fun installLeakCanary() {
//        LeakCanary.install(this)
//    }
//
//    private fun initTraceDroid() {
//        TraceDroid.init(this)
//        Log.setTAG("PassAndroid")
//    }

    companion object {
//        lateinit var kodein: Kodein
//        val tracker by lazy { kodein.typed.instance(Tracker::class.java) }
        //val passStore by lazy { kodein.typed.instance(PassStore::class.java) }
        lateinit var passStore : PassStore
        lateinit var settings : Settings
    }
}
