package pro.smartum.pkpass.model.comparator

import org.threeten.bp.ZonedDateTime
import pro.smartum.pkpass.model.pass.Pass
import java.util.*

open class PassByTimeComparator : Comparator<Pass> {

    override fun compare(lhs: Pass, rhs: Pass): Int {
        return calculateCompareForNullValues(lhs, rhs, { leftDate: ZonedDateTime, rightDate: ZonedDateTime ->
            return@calculateCompareForNullValues rightDate.compareTo(leftDate)
        })
    }

    protected fun calculateCompareForNullValues(lhs: Pass, rhs: Pass, foo: (leftDate: ZonedDateTime, rightDate: ZonedDateTime) -> Int): Int {
        val leftDate = extractPassDate(lhs)
        val rightDate = extractPassDate(rhs)

        if (leftDate == rightDate) {
            return 0
        }

        if (leftDate == null) {
            return 1
        }
        if (rightDate == null) {
            return -1
        }
        return foo(leftDate, rightDate)
    }

    private fun extractPassDate(pass: Pass): ZonedDateTime? {
        if (pass.calendarTimespan != null && pass.calendarTimespan!!.from != null) {
            return pass.calendarTimespan!!.from
        }

        if (pass.validTimespans != null && !pass.validTimespans!!.isEmpty()) {
            return pass.validTimespans!![0].from
        }

        return null
    }
}
