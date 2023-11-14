import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.stage.Stage

// HelloMVC1
// A simple MVC example inspired by Joseph Mack, http://www.austintek.com/mvc/
// This version doesn't use MVC at all! We'll use this to identify issues
// that we will address in later samples.
class NoMVC : Application() {

    var button = Button("?")
    var text = TextArea("")
    var counter = 0

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        // window name
        stage.title = this.javaClass.name

        val grid = GridPane()

        // Top group
        val pane1 = VBox()
        pane1.alignment = Pos.CENTER
        pane1.minHeight = 100.0
        pane1.children.add(button)
        grid.add(pane1, 0, 0) // col 0, row 0
        button.setMinSize(75.0, 25.0)
        button.setMaxSize(100.0, 50.0)
        button.onMouseClicked = EventHandler { increaseCounter() }

        // Bottom group
        val pane2 = VBox()
        pane2.children.add(text)
        grid.add(pane2, 0, 1) // col 0, row 1
        text.isWrapText = true
        text.isEditable = false
        text.onMouseClicked = EventHandler { increaseCounter() }

        // Add grid to a scene (and the scene to the stage)
        val scene = Scene(grid, 200.0, 200.0)
        stage.scene = scene
        stage.show()
    }

    private fun increaseCounter() {
        counter++
        // Button displays counter value
        button.text = counter.toString()

        // Text field shows one "X" per counter
        text.appendText("X")
    }
}