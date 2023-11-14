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
    var points = arrayOf(0.0, 150.0, 100.0, 150.0, 100.0, 50.0, 50.0, 0.0, 0.0, 50.0)

    override fun start(stage: Stage) {
        // top-level group to hold objects to draw
        val root = Group()

        // create a house shape model at the origin
        val house = Polygon()
        house.points.addAll(*points)
        house.fill = Color.BLUEVIOLET
        root.children.add(house)

        // create a second house and directly set scale and translate
        val house2 = Polygon()
        house2.points.addAll(*points)
        house2.fill = Color.DARKGOLDENROD
        house2.scaleX = 1.5
        house2.scaleY = 1.5
        house2.translateX = (width / 2)
        house2.translateY = (height / 2)
        root.children.add(house2)

        // add the scene to the stage and show it
        val scene = Scene(root, width, height, Color.WHITE)
        stage.scene = scene
        stage.show()
    }
}