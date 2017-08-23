package pro.smartum.pkpass.model.adapter

import android.graphics.Color
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import pro.smartum.pkpass.model.pass.PassImpl

class ColorAdapter {

    @ToJson
    internal fun toJson(@PassImpl.HexColor rgb: Int) = String.format("#%06x", rgb)

    @FromJson
    @PassImpl.HexColor
    internal fun fromJson(rgb: String) = Color.parseColor(rgb)
}
