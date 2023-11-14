import java.util.*

class Model internal constructor() {
    enum class TYPE {
        Deposit, Withdrawal, Balance
    }

    enum class CURRENCY {
        CDN, USD, MEX
    }

    private var transactions: ArrayList<Transaction> = ArrayList()
    private var viewModels = ArrayList<IViewModel>()
    fun addViewModel(viewModel: ViewModel) {
        viewModels.add(viewModel)
    }

    fun postTransaction(date: Date?, type: TYPE?, currency: String, amount: Double) {
        if (currency !== CURRENCY.CDN.toString()) {
            println("ERROR: We can only process USD currencies at this branch.")
        } else {
            transactions.add(Transaction(date ?: Date() , type ?: TYPE.Balance, CURRENCY.valueOf(currency), amount))
            update()
        }
    }

    fun getTransactions(): ArrayList<Transaction> {
        return transactions
    }

    private fun update() {
        for (viewModel in viewModels) {
            viewModel.update()
        }
    }
}