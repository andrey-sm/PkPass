package pro.smartum.pkpass.activity

import android.support.v7.app.AppCompatActivity
import pro.smartum.pkpass.app.App
import pro.smartum.pkpass.app.Settings
import pro.smartum.pkpass.storage.PassStore

open class PassAndroidActivity : AppCompatActivity() {

    val passStore: PassStore = App.passStore
    val settings: Settings = App.settings


}