import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.*

class Main : Application() {

    val width = 200.0
    val height = 200.0

    override fun start(stage: Stage) {

        val root = StackPane()

        val circle = Circle(32.0)

        // object to track state and update feedback
        val sf = StateFeedback(circle)

        with (circle) {
            strokeWidth = 3.0

            // hover IN
            setOnMouseEntered { sf.update(WidgetState.OVER) }
            // hover OUT (this will also transition out of DOWN state)
            setOnMouseExited { sf.update(WidgetState.DEFAULT) }
            // down IN
            setOnMousePressed { sf.update(WidgetState.DOWN) }
            // down OUT
            setOnMouseReleased {
                // need to be careful about extra transition out of down state
                if (sf.state == WidgetState.DOWN )
                    sf.update(WidgetState.OVER)
            }
            // disabled IN/OUT
            circle.disabledProperty().addListener { _, _, disable ->
                if (disable) { sf.update(WidgetState.DISABLED) }
                else { sf.update(WidgetState.DEFAULT) }
            }
        }

        root.children.addAll(circle)
        val scene = Scene(StackPane(root), width, height)

        // just to demo disable state
        scene.setOnKeyTyped {
            if (it.character == "d") {
                circle.isDisable = !circle.isDisable
            }
        }

        stage.title = "Input/States"
        stage.scene = scene
        stage.show()

//        startOnScreen2(stage)
    }
}