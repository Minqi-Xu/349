import javafx.application.Application
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import javafx.scene.control.TextInputDialog
import javafx.stage.Stage

// Shamelessly converted from Java example
// https://examples.javacodegeeks.com/desktop-java/javafx/dialog-javafx/javafx-dialog-example/

class Dialogs : Application() {
    override fun start(stage: Stage) {

        // informational
        val alert = Alert(AlertType.ERROR)
        alert.title = "Warning"
        alert.contentText = "This is invalid."
        alert.showAndWait()

        // confirmation
        val confirmation = Alert(AlertType.CONFIRMATION)
        confirmation.title = "Confirmation dialog"
        confirmation.contentText = "Do you wish to proceed?"
        val result1 = confirmation.showAndWait()

        if (result1.isPresent) {
            when (result1.get()) {
                ButtonType.OK -> println("OK")
                ButtonType.CANCEL -> println("CANCEL")
            }
        }

        // text input
        val dialog = TextInputDialog("")
        dialog.title = "Enter value"
        dialog.headerText = "Enter some text, or use default value."

        val result = dialog.showAndWait()
        if (result.isPresent) {
            println(result.get())
        }
    }


}