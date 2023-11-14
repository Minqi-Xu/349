import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import kotlin.system.exitProcess

class WidgetDemo : Application() {

    override fun start(stage: Stage) {
        val root = VBox()
        root.padding = Insets(10.0)
        root.spacing = 10.0
        val scene = Scene(root, 500.0, 200.0, Color.WHITE)

        val checkbox = CheckBox("Enable")
        val slider = Slider(0.0, 100.0, 50.0)
        val label = Label("")
        val button = Button("Exit")

        checkbox.isSelected = false
        slider.disableProperty().value = true

        checkbox.setOnAction {
                event -> slider.disableProperty().value = !(event.source as CheckBox).isSelected
        }


        slider.setOnMouseDragged { mouseEvent -> label.text = "slider " + (mouseEvent.source as Slider).value }
        button.setOnMouseClicked { Platform.exit() }

        root.children.add(checkbox)
        root.children.add(slider)
        root.children.add(label)
        root.children.add(button)

        stage.scene = scene
        stage.title = "WidgetDemo"
        stage.isResizable = false
        stage.setOnCloseRequest { exitProcess(0) }
        stage.show()
    }
}