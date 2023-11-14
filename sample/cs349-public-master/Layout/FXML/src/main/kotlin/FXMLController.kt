import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Label

// This is the controller class that handles input for our FXML form
// We specify this class in the FXML file when we create it.
class FXMLController {
    private var model: Model ?= null

    @FXML
    lateinit var label : Label  // lateinit b/c initialized by framework

    fun initialize() {}
    fun setModel(model: Model) {
        this.model = model
    }

    @FXML
    private fun handleButtonAction(event: ActionEvent) {
        label.text = "Button event handled"
        model?.setValue("Model updated from Controller")
        println(model?.getValue())
    }
}