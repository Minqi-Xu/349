import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.stage.Stage

/*
* PaintersAlgorithm.java (c) 2021 Jeff Avery
* Demonstrate stacking components on a canvas
* Shapes  will be painted in the order in which they are added to a list (i.e. back->front)
*/
class Main : Application() {
    override fun start(stage: Stage) {

        // list of shapes to draw
        val shapes: ArrayList<IShape> = ArrayList<IShape>()

        // create a scene graph (root group + canvas)
        val root = Group()
        val canvas = Canvas(400.0, 400.0)
        root.children.add(canvas)

        // add scene graph to stage
        stage.title = "Painter's Algorithm"
        stage.scene = Scene(root)
        stage.show()

        // create shapes and add to the list
        // they will be drawn in the order in which they are added to the list, so add background first!
        shapes.add(MyRectangle(10, 10, 380, 380, Color.GRAY)) // background
        shapes.add(MyRectangle(150, 150, 125, 150, Color.YELLOW)) // foreground
        shapes.add(MyCircle(90, 90, 60, Color.BLUE)) // foreground
        shapes.add(MyRectangle(200, 125, 100, 125, Color.GREEN)) // foreground

        // draw shapes on the canvas
        drawShapes(shapes, canvas)
    }

    // separate method that could be called later to draw shapes in order
    private fun drawShapes(shapes: ArrayList<IShape>, canvas: Canvas) {
        for (shape in shapes) {
            shape.draw(canvas)
        }
    }
}