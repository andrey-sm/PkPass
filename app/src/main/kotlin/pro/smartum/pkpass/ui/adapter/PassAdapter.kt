package pro.smartum.pkpass.ui.adapter

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pro.smartum.pkpass.R
import pro.smartum.pkpass.activity.PassViewActivity
import pro.smartum.pkpass.app.App
import pro.smartum.pkpass.app.Settings
import pro.smartum.pkpass.function.startActivityFromClass
import pro.smartum.pkpass.storage.PassStore
import pro.smartum.pkpass.storage.PassStoreProjection
import pro.smartum.pkpass.ui.pass_view_holder.CondensedPassViewHolder
import pro.smartum.pkpass.ui.pass_view_holder.PassViewHolder
import pro.smartum.pkpass.ui.pass_view_holder.VerbosePassViewHolder

class PassAdapter(private val passListActivity: AppCompatActivity, private val passStoreProjection: PassStoreProjection) : RecyclerView.Adapter<PassViewHolder>() {

    val passStore: PassStore = App.passStore
    val settings: Settings = App.settings

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PassViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)

        val res = inflater.inflate(R.layout.pass_list_item, viewGroup, false) as CardView
        if (settings.isCondensedModeEnabled()) {
            return CondensedPassViewHolder(res)
        } else {
            return VerbosePassViewHolder(res)
        }

    }

    override fun onBindViewHolder(viewHolder: PassViewHolder, position: Int) {
        val pass = passStoreProjection.passList[position]

        viewHolder.apply(pass, passStore, passListActivity)

        val root = viewHolder.view

        root.setOnClickListener {
            passStore.currentPass = pass
            passListActivity.startActivityFromClass(PassViewActivity::class.java)
        }

        root.setOnLongClickListener {
            ////////////Snackbar.make(root, R.string.please_use_the_swipe_feature, Snackbar.LENGTH_LONG).show()
            true
        }
    }

    override fun getItemId(position: Int) = position.toLong()
    private val list = passStoreProjection.passList
    override fun getItemCount() = list.size

}
