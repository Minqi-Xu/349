import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage?) {
        val button = Button("Select File")
        val label = Label()
        button.setOnMouseClicked {
            val chooser = FileChooser()
            chooser.title = "Choose a file"
            val file = chooser.showOpenDialog(stage)
            if (file != null) {
                label.text = label.text.toString() + "\n" + file.name + " selected"
            }
        }

        val root = VBox()
        root.alignment = Pos.TOP_LEFT
        root.padding = Insets(10.0)
        root.spacing = 10.0
        root.children.addAll(button, label)

        val scene = Scene(root, 320.0, 240.0)
        stage?.title = "FileChooser Demo"
        stage?.scene = scene
        stage?.show()

    }
}