package pro.smartum.pkpass.storage

import pro.smartum.pkpass.model.comparator.PassSortOrder
import pro.smartum.pkpass.model.pass.Pass
import java.util.*


class PassStoreProjection(private val passStore: PassStore, val topic: String, private val passSortOrder: PassSortOrder? = null) {

    var passList: List<Pass> = ArrayList()
        private set

    init {
        refresh()
    }

    fun refresh() {
        val newPassList = passStore.classifier.getPassListByTopic(topic)
        if (passSortOrder != null) {
            Collections.sort(newPassList, passSortOrder.toComparator())
        }

        passList = newPassList
    }
}
