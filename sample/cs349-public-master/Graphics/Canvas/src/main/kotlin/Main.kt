import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.stage.Stage

// Draw using a canvas class instead.
class Main : Application() {
    override fun start(stage: Stage) {

        // Create a scene graph with a root node
        // This will hold the objects that we want to display on the stage
        val root = Group()

        // Create a canvas as a drawing surface
        val canvas = Canvas(300.0, 300.0)

        // Use the graphics context to draw on a canvas
        val gc = canvas.graphicsContext2D
        gc.lineWidth = 5.0
        gc.stroke = Color.WHITE
        gc.fill = Color.WHITE
        gc.fillRect(75.0, 75.0, 100.0, 100.0)
        gc.fill = Color.AQUA
        gc.fillRect(100.0, 100.0, 100.0, 100.0)
        gc.fill = Color.YELLOWGREEN
        gc.fillRect(125.0, 125.0, 100.0, 100.0)

        // Add the canvas to the scene
        root.children.add(canvas)
        val scene = Scene(root, 300.0, 300.0, Color.BLACK)

        // Add the scene to the stage and show it
        stage.title = "Drawing Canvas"
        stage.scene = scene
        stage.show()
    }
}