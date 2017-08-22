package pro.smartum.pkpass.reader

import android.graphics.Color
import android.util.Log
import org.threeten.bp.ZonedDateTime
import pro.smartum.pkpass.function.readJSONSafely
import pro.smartum.pkpass.model.PassDefinitions
import pro.smartum.pkpass.model.pass.BarCode
import pro.smartum.pkpass.model.pass.Pass
import pro.smartum.pkpass.model.pass.PassImpl
import java.io.File

object PassReader {

    fun read(path: File): Pass {

        val pass = PassImpl(path.name)

        val file = File(path, "data.json")

        try {
            val plainJsonString = file.bufferedReader().readText()
            val pass_json = readJSONSafely(plainJsonString)!!

            if (pass_json.has("what")) {
                val what_json = pass_json.getJSONObject("what")
                pass.description = what_json.getString("description")
            }

            if (pass_json.has("meta")) {
                val meta_json = pass_json.getJSONObject("meta")
                pass.type = PassDefinitions.NAME_TO_TYPE[meta_json.getString("type")] ?: PassType.GENERIC
                pass.creator = meta_json.getString("organisation")
                pass.app = meta_json.getString("app")
            }

            if (pass_json.has("ui")) {
                val ui_json = pass_json.getJSONObject("ui")
                pass.accentColor = Color.parseColor(ui_json.getString("bgColor"))
            }

            if (pass_json.has("barcode")) {
                val barcode_json = pass_json.getJSONObject("barcode")
                val barcodeFormatString = barcode_json.getString("type")

                val barcodeFormat = BarCode.getFormatFromString(barcodeFormatString)
                val barCode = BarCode(barcodeFormat, barcode_json.getString("message"))
                pass.barCode = barCode

                if (barcode_json.has("altText")) {
                    barCode.alternativeText = barcode_json.getString("altText")
                }
            }

            if (pass_json.has("when")) {
                val dateTime = pass_json.getJSONObject("when").getString("dateTime")

                pass.calendarTimespan = PassImpl.TimeSpan()
                pass.calendarTimespan = PassImpl.TimeSpan(from = ZonedDateTime.parse(dateTime))
            }

        } catch (e: Exception) {
            Log.i("aLog", "PassParse Exception " + e)
        }

        return pass
    }

}
