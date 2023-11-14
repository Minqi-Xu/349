import Model.CURRENCY
import Model.TYPE
import java.util.*

class ViewModel internal constructor(var model: Model) : IViewModel {
    var views = ArrayList<IView>()
    fun addView(view: IView) {
        views.add(view)
    }

    fun postTransaction(date: Date?, type: TYPE?, currency: CURRENCY, amount: Double) {
        // convert currencies here before passing the transaction to the model
        // (our model only handles CDN currencies!)
        model.postTransaction(
            date,
            type,
            CURRENCY.CDN.toString(),
            convertToCDN(currency, amount)
        )
    }

    val transactions: ArrayList<Transaction>
        get() {
            val modelTransactions = model.getTransactions()
            val viewTransactions = ArrayList<Transaction>()
            for (modelTransaction in modelTransactions) {
                viewTransactions.add(
                    Transaction(
                        modelTransaction.datetime,
                        modelTransaction.type,
                        CURRENCY.CDN,
                        convertFromCDN(modelTransaction.currency, modelTransaction.amount)
                    )
                )
            }
            return viewTransactions
        }

    private fun convertFromCDN(currency: CURRENCY, amount: Double): Double {
        return when (currency) {
            CURRENCY.CDN -> amount
            CURRENCY.USD -> amount * 0.76
            CURRENCY.MEX -> amount * 20.33
            else -> amount
        }
    }

    private fun convertToCDN(currency: CURRENCY, amount: Double): Double {
        return when (currency) {
            CURRENCY.CDN -> amount
            CURRENCY.USD -> amount * 1.31
            CURRENCY.MEX -> amount * 0.049
            else -> amount
        }
    }

    override fun update() {
        for (view in views) {
            view.update()
        }
    }

    init {
        model.addViewModel(this)
    }
}