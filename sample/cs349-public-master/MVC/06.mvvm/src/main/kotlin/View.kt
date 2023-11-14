import javafx.event.Event
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import java.util.*

class View internal constructor(private var viewModel: ViewModel) : Pane(), IView {

    private val textTransactions: TextArea

    override fun update() {
        textTransactions.clear()
        val transactions: ArrayList<Transaction> = viewModel.transactions
        for (transaction in transactions) {
            textTransactions.appendText("$transaction\n")
        }
    }

    init {
        viewModel.addView(this)

        val labelActions = Label("Actions")
        val textActions = TextArea()
        val labelTransactions = Label("Transactions")
        textTransactions = TextArea("")

        val currencyType = ComboBox<Any?>()
        currencyType.items.add("CDN")
        currencyType.items.add("USD")
        currencyType.items.add("MEX")

        val deposit = Button("Deposit")
        val withdraw = Button("Withdraw")

        deposit.onAction = EventHandler {
            viewModel.postTransaction(
                Date(Calendar.getInstance().timeInMillis),
                Model.TYPE.Deposit,
                Model.CURRENCY.valueOf(currencyType.value as String),
                100.00
            )
            textActions.appendText("Deposit \$100 ${currencyType.value}\n")
        }
        withdraw.onAction = EventHandler {
            viewModel.postTransaction(
                Date(Calendar.getInstance().timeInMillis),
                Model.TYPE.Withdrawal,
                Model.CURRENCY.valueOf((currencyType.value as String)),
                100.00
            )
            textActions.appendText("Withdraw \$100 ${currencyType.value}")
        }
        currencyType.onAction = EventHandler {
            textActions.appendText("Switched to ${currencyType.value} currency\n")
        }
        currencyType.value = "CDN"

        val buttons = HBox(deposit, withdraw, currencyType)
        val vbox = VBox(buttons, labelActions, textActions, labelTransactions, textTransactions)
        vbox.alignment = Pos.CENTER
        children.add(vbox)
    }
}