import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.ColorPicker
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage

// Converted to Kotin from Oracle JavaFX sample
// https://docs.oracle.com/javafx/2/ui_controls/color-picker.htm

class ColourPicker : Application() {
    override fun start(stage: Stage) {
        stage.title = "ColorPicker"
        val scene = Scene(HBox(), 400.0, 100.0)
        val box = scene.root as HBox
        box.padding = Insets(5.0, 5.0, 5.0, 5.0)
        val colorPicker = ColorPicker()
        colorPicker.value = Color.CORAL
        val text = Text("Try the color picker!")
        text.font = Font.font("Verdana", 20.0)
        text.fill = colorPicker.value
        colorPicker.setOnAction {
            text.fill = colorPicker.value
        }
        box.children.addAll(colorPicker, text)
        stage.scene = scene
        stage.show()
    }
}