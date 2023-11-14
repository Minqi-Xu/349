import Model.CURRENCY
import Model.TYPE
import java.util.*

class Transaction internal constructor(// these should be read-only properties
    // could convert to a record class in later versions
    var datetime: Date, var type: TYPE, var currency: CURRENCY, var amount: Double
) {
    override fun toString(): String {
        return "$datetime :: $type :: $currency :: $amount"
    }
}