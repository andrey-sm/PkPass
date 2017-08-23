package pro.smartum.pkpass.reader

import android.graphics.Color
import org.threeten.bp.ZonedDateTime
import pro.smartum.pkpass.util.function.readJSONSafely
import pro.smartum.pkpass.model.PassDefinitions
import pro.smartum.pkpass.model.pass.BarCode
import pro.smartum.pkpass.model.pass.Pass
import pro.smartum.pkpass.model.pass.PassImpl
import pro.smartum.pkpass.util.Logger
import java.io.File

object PassReader {

    fun read(path: File): Pass {
        val pass = PassImpl(path.name)
        val file = File(path, "data.json")

        try {
            val plainJsonString = file.bufferedReader().readText()
            val passJson = readJSONSafely(plainJsonString)!!

            if (passJson.has("what")) {
                val whatJson = passJson.getJSONObject("what")
                pass.description = whatJson.getString("description")
            }

            if (passJson.has("meta")) {
                val metaJson = passJson.getJSONObject("meta")
                pass.type = PassDefinitions.NAME_TO_TYPE[metaJson.getString("type")] ?: PassType.GENERIC
                pass.creator = metaJson.getString("organisation")
                pass.app = metaJson.getString("app")
            }

            if (passJson.has("ui")) {
                val uiJson = passJson.getJSONObject("ui")
                pass.accentColor = Color.parseColor(uiJson.getString("bgColor"))
            }

            if (passJson.has("barcode")) {
                val barcodeJson = passJson.getJSONObject("barcode")
                val barcodeFormatString = barcodeJson.getString("type")

                val barcodeFormat = BarCode.getFormatFromString(barcodeFormatString)
                val barCode = BarCode(barcodeFormat, barcodeJson.getString("message"))
                pass.barCode = barCode

                if (barcodeJson.has("altText"))
                    barCode.alternativeText = barcodeJson.getString("altText")
            }

            if (passJson.has("when")) {
                val dateTime = passJson.getJSONObject("when").getString("dateTime")

                pass.calendarTimespan = PassImpl.TimeSpan()
                pass.calendarTimespan = PassImpl.TimeSpan(from = ZonedDateTime.parse(dateTime))
            }

        } catch (e: Exception) {
            Logger.e(javaClass, "PassParse Exception ", e)
        }

        return pass
    }

}
