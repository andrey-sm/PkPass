package pro.smartum.pkpass.model.comparator

import pro.smartum.pkpass.model.comparator.DirectionAwarePassByTimeComparator.Companion.DIRECTION_ASC
import pro.smartum.pkpass.model.pass.Pass
import java.util.*

class PassByTypeFirstAndTimeSecondComparator : Comparator<Pass> {

    private val passByTimeComparator = DirectionAwarePassByTimeComparator(DIRECTION_ASC)

    override fun compare(lhs: Pass, rhs: Pass): Int {
        val compareResult = lhs.type.compareTo(rhs.type)

        if (compareResult != 0)
            return compareResult
        return passByTimeComparator.compare(lhs, rhs)
    }
}
