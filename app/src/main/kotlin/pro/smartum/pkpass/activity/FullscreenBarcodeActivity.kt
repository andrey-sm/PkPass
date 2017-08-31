package pro.smartum.pkpass.activity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_fullscreen_barcode.*
import pro.smartum.pkpass.R
import pro.smartum.pkpass.util.Logger
import pro.smartum.pkpass.util.function.lockOrientation

class FullscreenBarcodeActivity : PassViewActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_barcode)
    }

    override fun onResume() {
        super.onResume()

        if (currentPass == null || currentPass.barCode == null) {
            Logger.e(javaClass, "FullscreenBarcodeActivity in bad state")
            finish() // this should never happen, but better safe than sorry
            return
        }
        setBestFittingOrientationForBarCode()

        fullscreen_barcode.setImageDrawable(currentPass.barCode!!.getBitmap(resources))

        if (currentPass.barCode!!.alternativeText != null) {
            alternativeBarcodeText.visibility = VISIBLE
            alternativeBarcodeText.text = currentPass.barCode!!.alternativeText
        } else
            alternativeBarcodeText.visibility = GONE
    }

    /**
     * QR and AZTEC are best fit in Portrait
     * PDF417 is best viewed in Landscape
     *
     * Main work is to avoid changing if we are already optimal
     * ( reverse orientation / sensor is the problem here ..)
     */
    private fun setBestFittingOrientationForBarCode() {
        if (currentPass.barCode!!.format!!.isQuadratic())
            processQuadraticBarcode()
        else processNonQuadraticBarcode()
    }

    private fun processQuadraticBarcode() {
        when (requestedOrientation) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT,
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT,
            ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT -> return  // do nothing

            else -> lockOrientation(Configuration.ORIENTATION_PORTRAIT)
        }
    }

    private fun processNonQuadraticBarcode() {
        when (requestedOrientation) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE,
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE,
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE,
            ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE -> return  // do nothing

            else -> lockOrientation(Configuration.ORIENTATION_LANDSCAPE)
        }
    }

    override fun onAttachedToWindow() {
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
    }
}
