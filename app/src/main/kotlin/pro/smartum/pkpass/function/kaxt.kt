package pro.smartum.pkpass.function

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import java.util.regex.Pattern
import kotlin.reflect.KClass

fun String?.parseColor(defaultValue: Int) : Int = when {
    this == null -> defaultValue

    startsWith("rgb") -> {
        parseColorRGBStyle(this, defaultValue)
    }

    startsWith("#") -> {
        try {
            Color.parseColor(this)
        } catch (ignored: Exception) {
            ignored.printStackTrace()
            // fall through to default color
            defaultValue
        }
    }

    else -> defaultValue
}


private fun parseColorRGBStyle(color_str: String, defaultValue: Int): Int {
    val pattern = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)")
    val matcher = pattern.matcher(color_str)

    if (matcher.matches()) {
        return 255 shl 24 or
                (Integer.valueOf(matcher.group(1))!! shl 16) or // r
                (Integer.valueOf(matcher.group(2))!! shl 8) or // g
                Integer.valueOf(matcher.group(3))!! // b

    }

    return defaultValue
}

fun WindowManager.getSizeAsPointCompat(): Point {
    val result = Point()
    if (Build.VERSION.SDK_INT > 12) {
        defaultDisplay.getSize(result)
    } else {
        @Suppress("DEPRECATION")
        result.set(defaultDisplay.width, defaultDisplay.height)
    }
    return result
}

fun WindowManager.getLargestSide(): Int {
    val sizeAsPoint = getSizeAsPointCompat()
    return Math.max(sizeAsPoint.x, sizeAsPoint.y)
}


fun WindowManager.getSmallestSide(): Int {
    val sizeAsPoint = getSizeAsPointCompat()
    return Math.min(sizeAsPoint.x, sizeAsPoint.y)
}

fun Context.startActivityFromClass(activityClass: Class<out Activity>) = startActivity(Intent(this, activityClass))
fun Context.startActivityFromClass(activityClass: KClass<out Activity>) = startActivity(Intent(this, activityClass.java))