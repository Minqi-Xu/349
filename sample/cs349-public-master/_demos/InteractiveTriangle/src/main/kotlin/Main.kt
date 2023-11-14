import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.stage.Stage
import kotlin.system.exitProcess

class Main : Application() {
    internal enum class STATE {
        SELECTION, DRAWING
    }

    private var state = STATE.SELECTION
    private var factory: TriangleFactory = TriangleFactory()

    inner class TriangleFactory {
        private var count = 0

        fun addtoCanvas(canvas: Pane, x: Double, y: Double) {
            val c = Circle(x, y, 10.0)
            c.setOnMouseClicked { }
            c.setOnMouseDragged { event ->
                if (state == STATE.SELECTION) {
                    c.centerX = event.x
                    c.centerY = event.y
                    // System.out.println("Circle: mouseDragged");
                }
            }
            canvas.children.add(c)
            count++
            if (count == 3) {
                val triangle = Triangle()
                movePoints(canvas, triangle)
                canvas.children.add(triangle)
                triangle.toFront()
                count = 0
            }
        }

        private fun movePoints(canvas: Pane, triangle: Triangle) {
            val n1: Circle = canvas.children[canvas.children.size - 1] as Circle
            canvas.children.remove(n1)
            triangle.add(n1)

            val n2: Circle = canvas.children[canvas.children.size- 1] as Circle
            canvas.children.remove(n2)
            triangle.add(n2)

            val n3: Circle = canvas.children[canvas.children.size - 1] as Circle
            canvas.children.remove(n3)
            triangle.add(n3)
        }
    }

    // Represents a triangle in the scene graph
    // Generated from exactly three points
    private inner class Triangle : Pane() {
        fun add(c: Circle?) {
            if (children.size < 3) {
                // add triangle
                children.add(c)

                // if we're on the last point, add the lines
                if (children.size == 3) {
                    val c1: Circle = children[0] as Circle
                    val c2: Circle = children[1] as Circle
                    val c3: Circle = children[2] as Circle
                    val l1 = Line(c1.centerX, c1.centerY, c2.centerX, c2.centerY)
                    val l2 = Line(c2.centerX, c2.centerY, c3.centerX, c3.centerY)
                    val l3 = Line(c3.centerX, c3.centerY, c1.centerX, c1.centerY)

                    c1.centerXProperty().bindBidirectional(l1.startXProperty())
                    c1.centerYProperty().bindBidirectional(l1.startYProperty())
                    c2.centerXProperty().bindBidirectional(l1.endXProperty())
                    c2.centerYProperty().bindBidirectional(l1.endYProperty())
                    c2.centerXProperty().bindBidirectional(l2.startXProperty())
                    c2.centerYProperty().bindBidirectional(l2.startYProperty())
                    c3.centerXProperty().bindBidirectional(l2.endXProperty())
                    c3.centerYProperty().bindBidirectional(l2.endYProperty())
                    c3.centerXProperty().bindBidirectional(l3.startXProperty())
                    c3.centerYProperty().bindBidirectional(l3.startYProperty())
                    c1.centerXProperty().bindBidirectional(l3.endXProperty())
                    c1.centerYProperty().bindBidirectional(l3.endYProperty())

                    // circles were already added to the scene graph, so just add lines
                    children.addAll(l1, l2, l3)
                }
            }
        }
    }

    override fun start(stage: Stage) {
        val select = Button("SELECT", ImageView("draw.png"))
        val draw = Button("Draw", ImageView("select.png"))
        draw.setOnMouseClicked {
            state = STATE.DRAWING
            draw.requestFocus()
            draw.text = "DRAW"
            select.text = "Select"
        }
        select.setOnMouseClicked {
            state = STATE.SELECTION
            select.requestFocus()
            select.text = "SELECT"
            draw.text = "Draw"
        }
        state = STATE.SELECTION
        select.requestFocus()
        val toolbar = HBox(select, draw)
        val canvas = Pane()
        val root = BorderPane()
        root.top = toolbar
        root.center = canvas
        val scene = Scene(root, 800.0, 600.0)

        canvas.setOnMousePressed { event ->
            if (state == STATE.DRAWING) {
                factory.addtoCanvas(canvas, event.x, event.y)
                // System.out.println("Canvas: mousePressed");
            }
        }
        toolbar.setOnKeyReleased { keyEvent ->
            if (keyEvent.code === KeyCode.ESCAPE || keyEvent.code === KeyCode.S) {
                state = STATE.SELECTION
                select.requestFocus()
                select.text = "SELECT"
                draw.text = "Draw"
            } else if (keyEvent.code === KeyCode.D) {
                state = STATE.DRAWING
                draw.requestFocus()
                select.text = "Select"
                draw.text = "DRAW"
            } else if (keyEvent.code === KeyCode.Q) {
                exitProcess(0)
            }
        }
        stage.scene = scene
        stage.title = "Triangle Drawing Demo"
        stage.isResizable = false
        stage.setOnCloseRequest { exitProcess(0) }
        stage.show()
    }
}