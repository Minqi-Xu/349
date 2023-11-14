import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.shape.Polygon
import javafx.stage.Stage

// create the window and run the demo
class Main : Application() {
    private val width = 500.0
    private val height = 500.0
    private var points = arrayOf(0.0, 150.0, 100.0, 150.0, 100.0, 50.0, 50.0, 0.0, 0.0, 50.0)

    override fun start(stage: Stage) {
        // top-level group to hold objects to draw
        val root = Group()

        // create a house shape model at the origin
        val house = Polygon()
        house.points.addAll(*points)
        house.fill = Color.BLUEVIOLET
        root.children.add(house)

        // create a second house that we'll scale and translate
        val house2 = Polygon()
        house2.points.addAll(*points)
        house2.fill = Color.DARKGOLDENROD
        root.children.add(house2)
        scale(house2, 1.5, 1.5)
        translate(house2, (width / 2), (height / 2)) // past the centre :/

        // add the scene to the stage and show it
        val scene = Scene(root, width, height, Color.WHITE)
        stage.scene = scene
        stage.show()
    }

    // non-matrix translate
    private fun translate(polygon: Polygon, tx: Double, ty: Double) {
        for (i in polygon.points.indices) {
            val oldVal = polygon.points[i]
            val delta = if (i % 2 == 0) tx else ty
            polygon.points[i] = oldVal + delta
        }
    }

    // non-matrix scale
    private fun scale(polygon: Polygon, sx: Double, sy: Double) {
        for (i in polygon.points.indices) {
            val oldVal = polygon.points[i]
            val scale = if (i % 2 == 0) sx else sy
            polygon.points[i] = oldVal * scale
        }
    }
}