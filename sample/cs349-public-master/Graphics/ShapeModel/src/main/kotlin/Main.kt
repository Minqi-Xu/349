import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.stage.Stage

/*
* SceneGraph
* Demonstrate simple scene graph that draws a house.
* Use the graphics context method to show cumulative effects
*/
class Main : Application() {
    private var scaleBy = 2.0
    private var rotateBy = 0.0

    inner class HouseModel {
        var xpoints = doubleArrayOf(-50.0, 50.0, 50.0, 0.0, -50.0)
        var ypoints = doubleArrayOf(75.0, 75.0, -25.0, -75.0, -25.0)
        var npoints = 5
    }

    override fun start(stage: Stage) {
        val house = HouseModel()
        val root = Group()
        val scene = Scene(root, 500.0, 500.0, Color.WHITE)
        val canvas = Canvas(500.0, 500.0)

        // get graphics context and use for all of our transformations
        val g = canvas.graphicsContext2D

        // draw the house in centre of screen
        g.translate(scene.width / 2, scene.height / 2)
        g.rotate(rotateBy)
        g.scale(scaleBy, scaleBy)
        g.fill = Color.BLUE
        g.strokePolygon(house.xpoints, house.ypoints, house.npoints)

        // save transform for later
        val save = g.transform

        // remember: these are all in "house coordinates"
        g.translate(-25.0, 0.0) // window centred 25 px
        g.scale(0.4, 0.4) // window is 40% house width
        drawWindow(g)

        // translate to right 50 px
        g.translate(50 / 0.4, 0.0)
        drawWindow(g)

        // draw third window twice to demo different coordinate frames

        // set transform to saved matrix to return to "House" coordinates
        // since this window is drawn in house coordinates, it will be
        // transformed with the house
        g.transform = save
        drawWindow(g, 0.0, -50.0, 45.0, 0.25)

        // set transform to identity to reset
        // this means third window is drawn in World Coordinates
        // and won't be transformed with the house
        g.transform.setToIdentity()

        // using function which has a model-to-world transform built in
        drawWindow(g, scene.width / 2, 89.0, 45.0, 0.5)

        // Add the scene to the stage and show it
        root.children.add(canvas)
        stage.scene = scene
        stage.show()
    }

    // (model position is centred at top left corner)
    // draws 100 x 100 window shape centred at 0,0
    private fun drawWindow(g: GraphicsContext) {
        g.fill = Color.BLACK
        g.fillRect(-50.0, -50.0, 100.0, 100.0)

        g.fill = Color.WHITE
        g.fillRect(-40.0, -40.0, 35.0, 35.0)
        g.fillRect(5.0, -40.0, 35.0, 35.0)
        g.fillRect(-40.0, 5.0, 35.0, 35.0)
        g.fillRect(5.0, 5.0, 35.0, 35.0)
    }

    // draws 100 x 100 window shape centred at 0,0
    private fun drawWindow(g: GraphicsContext, x: Double, y: Double, theta: Double, s: Double) {

        // save the current g2 transform matrix
        val save = g.transform

        // do the model to world transformation
        g.translate(x, y) // T
        g.rotate(theta) // R
        g.scale(s, s) // S

        // draws 100 x 100 window centred at 0,0
        drawWindow(g)
        // reset the transform to what it was before we drew the shape
        g.transform = save
    }
}