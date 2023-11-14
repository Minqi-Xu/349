/*
 * Transform3
 * Draw using a canvas, and graphics context (GC). This results in a different effect than
 * the previous methods, since the operations that we define in the Graphics Context apply
 * to *everything* that is drawn on the canvas. i.e. they are cumulative.
 */

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.stage.Stage


// Draw transformations using a canvas class.
class Main : Application() {
    private val width = 500.0
    private val height = 500.0

    // house at the origin
    private val xpoints = doubleArrayOf(0.0, 100.0, 100.0, 50.0, 0.0)
    private val ypoints = doubleArrayOf(150.0, 150.0, 50.0, 0.0, 50.0)
    private val npoints = 5

    override fun start(stage: Stage) {

        // Create a scene graph with a root node
        // This will hold the objects that we want to display on the stage
        val root = Group()
        val scene = Scene(root, width, height, Color.WHITE)

        // Create a canvas as a drawing surface
        val canvas = Canvas(width, height)

        // Use the graphics context to draw on the canvas
        val gc: GraphicsContext = canvas.graphicsContext2D
        gc.fill = Color.MEDIUMAQUAMARINE
        gc.fillPolygon(xpoints, ypoints, npoints)

        // Use the GC scale and translate methods
        gc.fill = Color.BLUEVIOLET
        gc.scale(2.0, 2.0)
        gc.translate(100.0, 100.0)
        gc.fillPolygon(xpoints, ypoints, npoints)

        // Use the scale and translate that were set in the GC above
        gc.fill = Color.YELLOW
        gc.fillRect(0.0, 0.0, 25.0, 25.0)

        // Add the canvas to the scene
        root.children.add(canvas)

        // Add the scene to the stage and show it
        stage.scene = scene
        stage.show()
    }
}