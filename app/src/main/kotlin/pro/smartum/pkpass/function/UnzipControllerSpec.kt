package pro.smartum.pkpass.function

import android.content.Context
import pro.smartum.pkpass.app.App
import pro.smartum.pkpass.function.UnzipPassController.FailCallback
import pro.smartum.pkpass.function.UnzipPassController.SuccessCallback
import pro.smartum.pkpass.storage.PassStore
import java.io.File

open class UnzipControllerSpec(var targetPath: File,
                               val context: Context,
                               val passStore: PassStore,
                               val onSuccessCallback: SuccessCallback?,
                               val failCallback: FailCallback?) {
    var overwrite = false

    constructor(context: Context, passStore: PassStore, onSuccessCallback: SuccessCallback?, failCallback: FailCallback?)
            : this(App.settings.getPassesDir(), context, passStore, onSuccessCallback, failCallback)

}
