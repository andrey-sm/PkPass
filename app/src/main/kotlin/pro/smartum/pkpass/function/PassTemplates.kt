package pro.smartum.pkpass.function

import android.content.res.Resources
import pro.smartum.pkpass.R
import pro.smartum.pkpass.model.pass.Pass
import pro.smartum.pkpass.model.pass.PassField
import pro.smartum.pkpass.model.pass.PassImpl
import pro.smartum.pkpass.reader.PassType
import java.util.*

val APP = "passandroid"

//fun createAndAddEmptyPass(passStore: PassStore, resources: Resources): Pass {
//    val pass = createBasePass()
//
//    pass.description = "custom Pass"
//
//    passStore.currentPass = pass
//    passStore.save(pass)
//
//    val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
//
//    try {
//        bitmap.compress(Bitmap.CompressFormat.PNG, 90, FileOutputStream(File(passStore.getPathForID(pass.id), PassBitmapDefinitions.BITMAP_ICON + ".png")))
//    } catch (ignored: FileNotFoundException) {
//        ignored.printStackTrace()
//    }
//
//    return pass
//}

fun createPassForImageImport(resources: Resources): Pass {
    return createBasePass().apply {
        description = resources.getString(R.string.image_import)

        fields = mutableListOf(
                PassField.create(R.string.field_source, R.string.field_source_image, resources),
                PassField.create(R.string.field_advice_label, R.string.field_advice_text, resources),
                PassField.create(R.string.field_note, R.string.field_note_image, resources, true)
        )
    }
}

fun createPassForPDFImport(resources: Resources): Pass {
    return createBasePass().apply {
        description = resources.getString(R.string.pdf_import)

        fields = mutableListOf(
                PassField.create(R.string.field_source, R.string.field_source_pdf, resources),
                PassField.create(R.string.field_advice_label, R.string.field_advice_text, resources),
                PassField.create(R.string.field_note, R.string.field_note_pdf, resources, true)
        )
    }
}

private fun createBasePass(): PassImpl {
    val pass = PassImpl(UUID.randomUUID().toString())
    pass.accentColor = 0xFF0000FF.toInt()
    pass.app = APP
    pass.type = PassType.EVENT
    return pass
}
