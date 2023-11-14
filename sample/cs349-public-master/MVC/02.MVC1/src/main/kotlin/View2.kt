import javafx.scene.control.TextArea
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox

internal class View2(
    private val model: Model,
    controller: Controller
    ) : VBox(), IView {

    private val text = TextArea("")

    // When notified by the model that things have changed,
    // update to display the new value
    override fun updateView() {
        println("View2: updateView")

        // display an 'X' for each counter value
        val s = StringBuilder()
        for (i in 0 until model.counterValue) s.append("X")
        text.text = s.toString()
    }

    init {
        // set textfield properties
        text.isWrapText = true
        text.isEditable = false

        // register the controller as a handler for this view
        text.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            controller.handle()
        }

        // add label widget to the pane
        children.add(text)

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}