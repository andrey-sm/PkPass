package pro.smartum.pkpass.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewConfiguration
import android.view.WindowManager
import pro.smartum.pkpass.model.pass.Pass


open class PassViewActivityBase : BaseActivity() {

    lateinit var currentPass: Pass
    private var mFullBrightnessSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // a little hack because I strongly disagree with the style guide here
        // ;-)
        // not having the Actionbar overflow menu also with devices with hardware
        // key really helps discoverability
        // http://stackoverflow.com/questions/9286822/how-to-force-use-of-overflow-menu-on-devices-with-menu-button
        try {
            val config = ViewConfiguration.get(this)
            val menuKeyField = ViewConfiguration::class.java.getDeclaredField("sHasPermanentMenuKey")
            if (menuKeyField != null) {
                menuKeyField.isAccessible = true
                menuKeyField.setBoolean(config, false)
            }
        } catch (ex: Exception) {
            // Ignore - but at least we tried ;-)
            ex.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()

        if (mPassStore.currentPass == null) {
            finish()
            return
        }

        currentPass = mPassStore.currentPass!!

        configureActionBar()

        if (settings.isAutomaticLightEnabled())
            setToFullBrightness()
    }

    protected fun configureActionBar() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    protected open fun refresh() {}


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setToFullBrightness() {
        val win = window
        val params = win.attributes
        params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
        win.attributes = params
        mFullBrightnessSet = true
        supportInvalidateOptionsMenu()
    }
}
