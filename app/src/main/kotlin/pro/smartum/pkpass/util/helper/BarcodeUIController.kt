package pro.smartum.pkpass.util.helper

import android.app.Activity
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.barcode.view.*
import pro.smartum.pkpass.util.function.getSmallestSide
import pro.smartum.pkpass.model.pass.BarCode

internal class BarcodeUIController(val rootView: View, private val barCode: BarCode?, activity: Activity, private val passViewHelper: PassViewHelper) {

    fun getBarcodeView() = rootView.vBarcodeImg

    private var mCurrentBarcodeWidth: Int = 0

    init {
        rootView.vZoomIn.setOnClickListener {
            setBarCodeSize(mCurrentBarcodeWidth + passViewHelper.mFingerSize)
        }

        rootView.vZoomOut.setOnClickListener {
            setBarCodeSize(mCurrentBarcodeWidth - passViewHelper.mFingerSize)
        }

        if (barCode != null) {
            val smallestSide = activity.windowManager.getSmallestSide()

            val bitmapDrawable = barCode.getBitmap(activity.resources)

            if (bitmapDrawable != null) {
                rootView.vBarcodeImg.setImageDrawable(bitmapDrawable)
                rootView.vBarcodeImg.visibility = VISIBLE
            } else {
                rootView.vBarcodeImg.visibility = GONE
            }

            if (barCode.alternativeText != null) {
                rootView.vBarcodeAltText.text = barCode.alternativeText
                rootView.vBarcodeAltText.visibility = VISIBLE
            } else {
                rootView.vBarcodeAltText.visibility = GONE
            }

            setBarCodeSize(smallestSide / 2)
        } else {
            passViewHelper.setBitmapSafe(rootView.vBarcodeImg, null)
            rootView.vZoomIn.visibility = GONE
            rootView.vZoomOut.visibility = GONE
        }
    }


    private fun setBarCodeSize(width: Int) {
        rootView.vZoomOut.visibility = if (width < passViewHelper.mFingerSize * 2) INVISIBLE else VISIBLE

        if (width > passViewHelper.windowWidth - passViewHelper.mFingerSize * 2)
            rootView.vZoomIn.visibility = INVISIBLE
        else rootView.vZoomIn.visibility = VISIBLE

        mCurrentBarcodeWidth = width
        val quadratic = barCode!!.format!!.isQuadratic()
        rootView.vBarcodeImg.layoutParams = LinearLayout.LayoutParams(width, if (quadratic) width else ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
