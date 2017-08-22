package pro.smartum.pkpass.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pro.smartum.pkpass.app.App
import pro.smartum.pkpass.storage.PassStore
import pro.smartum.pkpass.task.ImportAndShowAsyncTask

class PassImportActivity : AppCompatActivity() {

//    val tracker: Tracker = App.kodein.instance()
    val passStore: PassStore = App.passStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.data == null || intent.data.scheme == null) {
            //tracker.trackException("invalid_import_uri", false)
            finish()
        } else {
            //Toast.makeText(this, "Import", Toast.LENGTH_SHORT).show()
            ImportAndShowAsyncTask(this, intent.data).execute()
        }
    }

}
