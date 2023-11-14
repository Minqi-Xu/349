import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Shape
import javafx.stage.Stage
import kotlin.random.Random

// Shamelessly borrowed from a useful StackOverflow post
// https://stackoverflow.com/questions/15013913/checking-collision-of-shapes-with-javafx
class Main : Application() {
    private lateinit var nodes: ArrayList<Shape>

    inner class Delta {
        var x = 0.0
        var y = 0.0
    }

    override fun start(stage: Stage) {



        val root = Group()

        val scene = Scene(root, 400.0, 400.0)
        nodes = ArrayList()
        repeat(3 ) {
            nodes.add(Circle(
                Random.nextDouble(60.0, 340.0),
                Random.nextDouble(60.0, 340.0),
                30.0))
        }


        for (s: Shape in nodes) {
            s.stroke = Color.BLACK
            s.strokeWidth = 1.0
            s.fill = Color.GREEN
            setDragListeners(s)
        }
        root.children.addAll(nodes)

        checkShapeIntersection(nodes[nodes.size - 1])

        stage.title = "Hit-Testing/Collisions"
        stage.scene = scene
        stage.show()
    }

    private fun setDragListeners(shape: Shape) {
        // record a delta distance for the drag and drop operation.
        val dragDelta = Delta()
        shape.onMousePressed = EventHandler {
            dragDelta.x = shape.layoutX - it.sceneX
            dragDelta.y = shape.layoutY - it.sceneY
            shape.cursor = Cursor.NONE
        }

        shape.onMouseReleased = EventHandler {
            shape.cursor = Cursor.HAND
        }

        shape.onMouseDragged = EventHandler {
                shape.layoutX = it.sceneX + dragDelta.x
                shape.layoutY = it.sceneY + dragDelta.y
                checkShapeIntersection(shape)
        }
    }

    private fun checkShapeIntersection(shape: Shape) {
        var collisionDetected = false

        for (s: Shape in nodes) {
            if (s !== shape) {

                // intersect implies that shapes overlap
                val intersect = Shape.intersect(shape, s)

                if (intersect.boundsInLocal.width != -1.0) {
                    collisionDetected = true
                    s.fill = Color.GREENYELLOW
                } else {
                    s.fill = Color.GREEN
                }
            }
        }
        if (collisionDetected) {
            shape.fill = Color.GREENYELLOW
        } else {
            shape.fill = Color.GREEN
        }
    }
}
