package pro.smartum.pkpass.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_pass_list.view.*
import pro.smartum.pkpass.R
import pro.smartum.pkpass.app.App
import pro.smartum.pkpass.app.Settings
import pro.smartum.pkpass.storage.PassStore
import pro.smartum.pkpass.storage.PassStoreProjection
import pro.smartum.pkpass.ui.adapter.PassAdapter

class PassListFragment : Fragment() {

    private lateinit var mPassStoreProjection: PassStoreProjection
    private lateinit var mAdapter: PassAdapter

    private val mPassStore: PassStore = App.passStore
    private val mSettings: Settings = App.settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPassStoreProjection = PassStoreProjection(mPassStore, mSettings.getSortOrder())
        mAdapter = PassAdapter(activity as AppCompatActivity, mPassStoreProjection)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pass_list, container, false)

        view.vPassRecyclerView.adapter = mAdapter
        view.vPassRecyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }
}
