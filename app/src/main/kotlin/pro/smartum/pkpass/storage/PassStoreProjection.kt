package pro.smartum.pkpass.storage

import pro.smartum.pkpass.app.App
import pro.smartum.pkpass.model.comparator.PassSortOrder
import pro.smartum.pkpass.model.pass.Pass
import java.io.File
import java.util.*


class PassStoreProjection(private val passStore: PassStore, private val passSortOrder: PassSortOrder? = null) {

    var passList: MutableList<Pass> = ArrayList<Pass>()
        private set

    init { refresh() }

    private fun refresh() {
        val path: File = App.settings.getPassesDir()
        val allPasses = path.listFiles()

        passList.clear()
        allPasses?.forEach {
            var pass = passStore.getPassbookForId(it.name)
            if(pass != null)
                passList.add(pass)
        }

        if (passSortOrder != null)
            Collections.sort(passList, passSortOrder.toComparator())
    }
}
