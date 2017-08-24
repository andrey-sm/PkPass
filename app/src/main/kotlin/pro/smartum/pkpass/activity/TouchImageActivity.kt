package pro.smartum.pkpass.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import pro.smartum.pkpass.app.App
import pro.smartum.pkpass.app.KEY_IMAGE
import pro.smartum.pkpass.storage.PassStore
import pro.smartum.pkpass.ui.views.TouchImageView

class TouchImageActivity : AppCompatActivity() {

    val passStore: PassStore = App.passStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = TouchImageView(this)

        setContentView(webView)

        webView.setImageBitmap(passStore.currentPass!!.getBitmap(passStore, intent.getStringExtra(KEY_IMAGE)))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
