import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.input.MouseEvent
import javafx.scene.shape.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage

class Main : Application() {

    override fun start(stage: Stage) {

        val pane = Pane()

        // JavaFX Circle Shape
        val circle1 = Circle(140.0, 120.0, 25.0)
        circle1.fill = null
        circle1.stroke = Color.BLUE
        circle1.strokeWidth = 10.0
        pane.children.add(circle1)

        val circle2 = Circle(200.0, 120.0, 25.0)
        circle2.fill = Color.BLUE
        pane.children.add(circle2)

        // JavaFX Rectangle Shape
        val rect = Rectangle(150.0, 20.0, 100.0, 50.0)
        rect.fill = Color.BLUE
        pane.children.add(rect)

        // JavaFX Polygon Shape
        val poly = Polygon(0.0, 150.0,
                                100.0, 150.0,
                                100.0, 50.0,
                                50.0, 0.0,
                                0.0, 50.0)
        poly.fill = Color.BLUE
        pane.children.add(poly)

        val line = Line(20.0, 200.0, 250.0, 250.0)
        line.strokeWidth = 10.0
        line.stroke = Color.BLUE
        pane.children.add(line)

        stage.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
            // do hit-test against each shape in pane
            for (node in pane.children) {
                // cast node to shape
                // TODO: should really handle non-Shape nodes
                val shape = node as Shape
                // build-in JavaFX hit-test
                val hit = shape.contains(e.x, e.y)
                // helper function to handle color highlighting
                // based on hit-test
                highlight(shape, hit)
            }
        }

        val scene = Scene(pane, 300.0, 300.0)
        stage.title = "Hit-Testing/Shape"
        stage.scene = scene
        // could make it resizable, but then need more logic to handle ball outside
        // scene when resized smaller
        stage.isResizable = false
        stage.show()
    }

    fun highlight(shape: Shape, hl: Boolean) {
        // handle filled and outlined shapes differently
        if (shape.fill == null)
            shape.stroke = if (hl) Color.LIGHTSKYBLUE else Color.BLUE
        else
            shape.fill = if (hl) Color.LIGHTSKYBLUE else Color.BLUE
    }
}