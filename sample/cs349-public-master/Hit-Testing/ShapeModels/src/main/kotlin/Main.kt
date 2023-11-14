import javafx.application.Application
import javafx.geometry.Point2D
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color

import javafx.stage.Stage

class Main : Application() {

    override fun start(stage: Stage) {

        val displayList = mutableListOf<Shape>()

        // Circle with fill
        val circle1 = Circle(75.0, 50.0, 25.0)
        with (circle1) {
            fill = Color.RED
            stroke = null
            strokeWidth = 10.0
            displayList.add(this)
        }

        // Circle without fill
        val circle2 = Circle(225.0, 50.0, 25.0)
        with (circle2) {
            fill = null
            stroke = Color.RED
            strokeWidth = 10.0
            displayList.add(this)
        }

        // Rectangle with fill
        val rect1 = Rectangle(25.0, 100.0, 100.0, 50.0)
        with (rect1) {
            fill = Color.RED
            stroke = Color.RED
            strokeWidth = 10.0
            displayList.add(this)
        }

        // Rectangle without fill
        val rect2 = Rectangle(175.0, 100.0, 100.0, 50.0)
        with (rect2) {
            fill = null
            stroke = Color.RED
            strokeWidth = 10.0
            displayList.add(this)
        }

        // list of polygon points to make that house shape again
        val polyPoints = listOf( Point2D(0.0, 150.0),
            Point2D(100.0, 150.0),
            Point2D(100.0, 50.0),
            Point2D(50.0, 0.0),
            Point2D(0.0, 50.0))

        // Polygon with fill
        val poly = Polygon(polyPoints.map { p -> Point2D(p.x + 25.0, p.y + 175.0)})
        with (poly) {
            fill = Color.RED
            stroke = null
            strokeWidth = 10.0
            displayList.add(this)
        }

        // Polygon without fill
        val poly2 = Polygon(polyPoints.map { p -> Point2D(p.x + 175.0, p.y + 175.0)})
        with (poly2) {
            fill = null
            stroke = Color.RED
            strokeWidth = 10.0
            displayList.add(this)
        }

        // Line (fill doesn't matter)
        val line = Line(50.0, 360.0, 250.0, 380.0)
        with (line) {
            stroke = Color.RED
            strokeWidth = 10.0
            displayList.add(this)
        }

        // create a canvas to draw the shapes
        val canvas = Canvas(300.0, 400.0)

        // render all shapes to canvas
        fun renderDisplayList() {
            val gc = canvas.graphicsContext2D
            gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
            for (s in displayList) {
                s.draw(gc)
            }
        }

        // setup a hit-test test event
        stage.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->

            // iterate through ALL shapes, hit test each one
            for (s in displayList) {
                if (s.hittest(e.x, e.y)) {
                    if (s.isFilled) s.fill = Color.ORANGE
                    if (s.isStroked) s.stroke = Color.ORANGE
                } else {
                    if (s.isFilled) s.fill = Color.RED
                    if (s.isStroked) s.stroke = Color.RED
                }
            }
            renderDisplayList()
        }

        // first render
        renderDisplayList()

        val scene = Scene(Pane(canvas), 300.0, 400.0)
        // use a crosshair cursor for more precise testing
        scene.setCursor(Cursor.CROSSHAIR)
        stage.title = "Hit-Testing/ShapeModels"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }


}