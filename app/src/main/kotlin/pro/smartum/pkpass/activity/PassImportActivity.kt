package pro.smartum.pkpass.activity

import android.os.Bundle
import pro.smartum.pkpass.task.ImportAndShowAsyncTask
import pro.smartum.pkpass.util.Logger

class PassImportActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.data == null || intent.data.scheme == null) {
            Logger.e(javaClass, "invalid_import_uri")
            finish()
            return
        }

        ImportAndShowAsyncTask(this, intent.data).execute()
    }
}
