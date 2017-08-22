package pro.smartum.pkpass.model

import pro.smartum.pkpass.reader.PassType
import pro.smartum.pkpass.reader.PassType.*


object PassDefinitions {

    val TYPE_TO_NAME = mapOf(COUPON to "coupon",
            PassType.EVENT to "eventTicket",
            BOARDING to "boardingPass",
            GENERIC to "generic",
            LOYALTY to "storeCard")

    val NAME_TO_TYPE = TYPE_TO_NAME.entries.associate { it.value to it.key }

}
