import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox

internal class View1(
    private val model: Model,
    controller: Controller
    ) : VBox(), IView {

    private val button = Button("?")

    // When notified by the model that things have changed,
    // update to display the new value
    override fun updateView() {
        println("View: updateView")
        button.text = model.counterValue.toString()
    }

    init {
        // setup the view (i.e. group+widget)
        this.alignment = Pos.CENTER
        this.minHeight = 100.0

        button.setMinSize(75.0, 25.0)
        button.setMaxSize(100.0, 50.0)

        // register the controller as a handler for this view
        button.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            controller.handle()
        }

        // add button widget to the pane
        children.add(button)

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}