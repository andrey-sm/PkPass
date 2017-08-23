package pro.smartum.pkpass.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.pass_recycler.view.*
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
        // FIXME Remove 'new' arg
        mPassStoreProjection = PassStoreProjection(mPassStore, "new", mSettings.getSortOrder())
        mAdapter = PassAdapter(activity as AppCompatActivity, mPassStoreProjection)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pass_recycler, container, false)

        view.pass_recyclerview.adapter = mAdapter
        view.pass_recyclerview.layoutManager = LinearLayoutManager(activity)

        return view
    }
}
