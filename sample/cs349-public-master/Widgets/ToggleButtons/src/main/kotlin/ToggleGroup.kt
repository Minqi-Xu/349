import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.TilePane
import javafx.stage.Stage

class ToggleDemo : Application() {
    override fun start(stage: Stage) {

        // create toggle buttons and toggle group to control behaviour
        val toggles = ToggleGroup()
        val button1 = ToggleButton("one")
        val button2 = ToggleButton("two")
        val button3 = ToggleButton("three")
        toggles.toggles.addAll(button1, button2, button3)

        // setup the layout
        val tilePane = TilePane()
        tilePane.children.add(button1)
        tilePane.children.add(button2)
        tilePane.children.add(button3)

        // display the scene
        val scene = Scene(tilePane)
        stage.scene = scene
        stage.show()
    }
}