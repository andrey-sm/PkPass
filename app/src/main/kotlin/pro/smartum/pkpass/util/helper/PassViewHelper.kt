package pro.smartum.pkpass.util.helper

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import pro.smartum.pkpass.R
import pro.smartum.pkpass.function.getSizeAsPointCompat

class PassViewHelper(private val context: Activity) {

    val mFingerSize by lazy { context.resources.getDimensionPixelSize(R.dimen.finger) }

    fun setBitmapSafe(imageView: ImageView, bitmap: Bitmap?) {
        if (bitmap == null)
            imageView.visibility = View.GONE
        else {
            imageView.setImageBitmap(bitmap)
            imageView.visibility = View.VISIBLE
            imageView.layoutParams = getLayoutParamsSoThatWeHaveMinimumAFingerInHeight(imageView, bitmap)
        }
    }

    fun getLayoutParamsSoThatWeHaveMinimumAFingerInHeight(imageView: ImageView, bitmap: Bitmap)
            = imageView.layoutParams!!.apply {
        height = if (bitmap.height < mFingerSize)
            mFingerSize
        else LinearLayout.LayoutParams.WRAP_CONTENT
    }

    val windowWidth by lazy { context.windowManager.getSizeAsPointCompat().x }
}
