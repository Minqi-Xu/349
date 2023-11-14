import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font

/*
 * A simple extended textfield widget with input validation
 */
class ValidatableTextField(
    val valParams: ValidationParams,
    initialText: String = "",
    filter: Boolean = true,
    validateKeystrokes: Boolean = true,
): VBox() {

    val textField = TextField()
    val label = Label()

    // useful for other nodes to check on status (like submit button)
    val isValid
        get() = textField.text.matches(valParams.validationRegex)

    // updates the message and other error feedback
    fun errorState(text: String): Boolean {
        // good to always allow empty value with no error
        val valid = text.matches(valParams.validationRegex) || text == ""

        if (valid) {
            label.textFill = Color.GRAY
            label.text = valParams.promptMessage
            textField.setStyle("")
        } else {
            label.textFill = Color.DARKRED
            label.text =  valParams.errorMessage
            textField.setStyle("-fx-border-color: #ee0000 ; -fx-border-width: 1px ;")
        }
         return valid
    }

    init {
        // box styling
        spacing = 2.0
        prefWidth = 250.0
        isFillWidth = false

        // setup the textfield and label to show messages
        textField.text = initialText
        textField.prefWidth = 150.0

        label.font = Font(10.0)

        this.children.addAll(textField, label)

        // filter out any characters that can't be used in the input
        if (filter) {
            textField.textFormatter = TextFormatter<String> { chg ->
                // println("'${chg.controlText}' '${chg.text}' '${chg.controlNewText}'")
                if (chg.controlNewText.matches(valParams.filterRegex))
                    chg
                else
                    null
            }
        }

        // update error message on each keystroke
        if (validateKeystrokes) {
            textField.textProperty().addListener { _, _, _ ->
                errorState(textField.text)
            }
        }

        // update error message on press enter or loss of focus
        textField.setOnAction {
            errorState(textField.text)
        }
        textField.focusedProperty().addListener { _, _, _ ->
            errorState(textField.text)
        }

        // initialize state
        errorState(textField.text)
    }
}