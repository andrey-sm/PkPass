package pro.smartum.pkpass.activity

import android.support.v7.app.AppCompatActivity
import pro.smartum.pkpass.app.App
import pro.smartum.pkpass.app.Settings
import pro.smartum.pkpass.storage.PassStore

open class BaseActivity : AppCompatActivity() {

    val mPassStore: PassStore = App.passStore
    val settings: Settings = App.settings
}
