package pro.smartum.pkpass.ui.fragment

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
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


    private lateinit var passStoreProjection: PassStoreProjection
    private lateinit var adapter: PassAdapter

    val passStore: PassStore = App.passStore
    val settings: Settings = App.settings
    //val bus: EventBus = App.kodein.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.pass_recycler, container, false)

        passStoreProjection = PassStoreProjection(passStore, /*arguments.getString(BUNDLE_KEY_TOPIC)!!*/"new", settings.getSortOrder())
        adapter = PassAdapter(activity as AppCompatActivity, passStoreProjection)

        inflate.pass_recyclerview.adapter = adapter

        inflate.pass_recyclerview.layoutManager = LinearLayoutManager(activity)

        val simpleItemTouchCallback = object : SimpleCallback(0, LEFT or RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder)
                    = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                this@PassListFragment.onSwiped(viewHolder.adapterPosition, swipeDir)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(inflate.pass_recyclerview)

       //////////////// bus.register(this)
        return inflate
    }

    @VisibleForTesting
    fun onSwiped(pos: Int, swipeDir: Int) {
        val pass = passStoreProjection.passList[pos]
        val nextTopic = passStore.classifier.getTopicWithOffset(pass, if (swipeDir == LEFT) -1 else 1)

        if (nextTopic != null) {
            ///////moveWithUndoSnackbar(passStore.classifier, pass, nextTopic, activity)
        } else {
            //////////////////MoveToNewTopicUI(activity, passStore, pass).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ////////////////////bus.unregister(this)
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onPassStoreChangeEvent(passStoreChangeEvent: PassStoreChangeEvent) {
//        passStoreProjection.refresh()
//        adapter.notifyDataSetChanged()
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onScanFinishedEvent(scanFinishedEvent: ScanFinishedEvent) {
//        passStoreProjection.refresh()
//        adapter.notifyDataSetChanged()
//
//    }

    companion object {

        private val BUNDLE_KEY_TOPIC = "topic"

        fun newInstance(topic: String) = PassListFragment().apply {
            arguments = Bundle()
            arguments.putString(BUNDLE_KEY_TOPIC, topic)
        }
    }

}
