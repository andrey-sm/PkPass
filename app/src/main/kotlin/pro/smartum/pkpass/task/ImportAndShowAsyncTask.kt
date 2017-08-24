package pro.smartum.pkpass.task

import android.app.ProgressDialog
import android.net.Uri
import android.os.AsyncTask
import pro.smartum.pkpass.R
import pro.smartum.pkpass.activity.PassImportActivity
import pro.smartum.pkpass.activity.PassViewActivity
import pro.smartum.pkpass.util.UnzipPassController
import pro.smartum.pkpass.util.UnzipPassController.InputStreamUnzipControllerSpec
import pro.smartum.pkpass.util.function.fromURI
import pro.smartum.pkpass.util.function.startActivityFromClass
import pro.smartum.pkpass.model.InputStreamWithSource
import pro.smartum.pkpass.ui.dialog.UnzipPassDialog

internal class ImportAndShowAsyncTask(val passImportActivity: PassImportActivity, val intent_uri: Uri) : AsyncTask<Void, Void, InputStreamWithSource?>() {

    private val mWaitProgressDialog by lazy {
        ProgressDialog(passImportActivity).apply {
            setMessage(passImportActivity.getString(R.string.please_wait))
            setCancelable(false)
        }
    }

    override fun doInBackground(vararg params: Void) = fromURI(passImportActivity, intent_uri)

    override fun onPreExecute() {
        mWaitProgressDialog.show()
        super.onPreExecute()
    }

    override fun onPostExecute(result: InputStreamWithSource?) {
        super.onPostExecute(result)

        if (result == null) {
            dismissProgressDialog()

            passImportActivity.finish()
            //TODO show some error here?!
            return  // no result -> no work here
        }

        if (passImportActivity.isFinishing) { // finish with no UI/Dialogs
            // let's do it silently TODO check if we need to jump to a service here as the activity is dying
            val spec = InputStreamUnzipControllerSpec(result, passImportActivity.application, passImportActivity.mPassStore, null, null)
            UnzipPassController.processInputStream(spec)
            return
        }

        UnzipPassDialog.show(result, passImportActivity, passImportActivity.mPassStore) { path ->
            // TODO this is kind of a hack - there should be a better way
            val id = path.split("/".toRegex()).dropLastWhile(String::isEmpty).toTypedArray().last()

            val passbookForId = passImportActivity.mPassStore.getPassbookForId(id)
            passImportActivity.mPassStore.currentPass = passbookForId

            passImportActivity.startActivityFromClass(PassViewActivity::class.java)
            passImportActivity.finish()
        }
    }

    private fun dismissProgressDialog() {
        if (!passImportActivity.isFinishing && mWaitProgressDialog.isShowing) {
            try {
                mWaitProgressDialog.dismiss()
            } catch (ignored: Exception) {
                ignored.printStackTrace()
            }
        }
    }
}
