package pro.smartum.pkpass.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.app.TaskStackBuilder
import android.support.v4.text.util.LinkifyCompat
import android.text.util.Linkify
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_pass_view.*
import kotlinx.android.synthetic.main.activity_pass_view_base.*
import kotlinx.android.synthetic.main.item_pass.*
import kotlinx.android.synthetic.main.pass_view_extra_data.*
import pro.smartum.pkpass.R
import pro.smartum.pkpass.app.App.Companion.passStore
import pro.smartum.pkpass.app.KEY_IMAGE
import pro.smartum.pkpass.model.PassBitmapDefinitions
import pro.smartum.pkpass.model.pass.Pass
import pro.smartum.pkpass.ui.adapter.holder.VerbosePassViewHolder
import pro.smartum.pkpass.util.HtmlCompat
import pro.smartum.pkpass.util.helper.BarcodeUIController
import pro.smartum.pkpass.util.helper.PassViewHelper

class PassViewActivity : PassViewActivityBase() {

    private val mPassViewHelper by lazy { PassViewHelper(this) }

    internal fun processImage(view: ImageView, name: String, pass: Pass) {
        val bitmap = pass.getBitmap(mPassStore, name)
        if (bitmap != null && bitmap.width > 300) {
            view.setOnClickListener {
                val intent = Intent(view.context, TouchImageActivity::class.java)
                intent.putExtra(KEY_IMAGE, name)
                startActivity(intent)
            }
        }
        mPassViewHelper.setBitmapSafe(view, bitmap)
    }

    override fun refresh() {
        super.refresh()

        BarcodeUIController(findViewById(android.R.id.content), currentPass.barCode, this, mPassViewHelper)

        processImage(vLogoImgView, PassBitmapDefinitions.BITMAP_LOGO, currentPass)
        processImage(vFooterImgView, PassBitmapDefinitions.BITMAP_FOOTER, currentPass)
        processImage(vThumbnailImgView, PassBitmapDefinitions.BITMAP_THUMBNAIL, currentPass)
        processImage(vStripImgView, PassBitmapDefinitions.BITMAP_STRIP, currentPass)

        if (vMapContainer != null && !(currentPass.locations.isNotEmpty()))
            vMapContainer.visibility = View.GONE

        val backStr = StringBuilder()

        vFrontFieldContainer.removeAllViews()

        for (field in currentPass.fields) {
            if (field.hide) {
                backStr.append(field.toHtmlSnippet())
            } else {
                val v = layoutInflater.inflate(R.layout.main_field_item, vFrontFieldContainer, false)
                val key = v.findViewById(R.id.vKey) as TextView
                key.text = field.label
                val value = v.findViewById(R.id.vValue) as TextView
                value.text = field.value

                vFrontFieldContainer.addView(v)
                LinkifyCompat.addLinks(key, Linkify.ALL)
                LinkifyCompat.addLinks(value, Linkify.ALL)
            }
        }


        if (backStr.isNotEmpty()) {
            vBackFields.text = HtmlCompat.fromHtml(backStr.toString())
            vMoreTextView.visibility = View.VISIBLE
        } else vMoreTextView.visibility = View.GONE

        LinkifyCompat.addLinks(vBackFields, Linkify.ALL)

        val passViewHolder = VerbosePassViewHolder(pass_card)
        passViewHolder.apply(currentPass, passStore, this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // disableRotation()

        setContentView(R.layout.activity_pass_view)

        val passExtrasView = layoutInflater.inflate(R.layout.pass_view_extra_data, vPassExtrasContainer, false)
        vPassExtrasContainer.addView(passExtrasView)

    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        vMoreTextView.setOnClickListener {
            if (vBackFields.visibility == View.VISIBLE) {
                vBackFields.visibility = View.GONE
                vMoreTextView.setText(R.string.more)
            } else {
                vBackFields.visibility = View.VISIBLE
                vMoreTextView.setText(R.string.less)
            }
        }

//        barcode_img.setOnClickListener {
//            startActivityFromClass(FullscreenBarcodeActivity::class.java)
//        }

        setSupportActionBar(vToolbar)

        configureActionBar()
        refresh()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            val upIntent = NavUtils.getParentActivityIntent(this)
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities()
                finish()
            } else {
                NavUtils.navigateUpTo(this, upIntent)
            }
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun onAttachedToWindow() = window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
}
