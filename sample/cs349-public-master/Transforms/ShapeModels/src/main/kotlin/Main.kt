import javafx.application.Application
import javafx.geometry.Point2D
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.transform.Affine

import javafx.stage.Stage
import kotlin.math.*

class Main : Application() {

    override fun start(stage: Stage) {

        val displayList = mutableListOf<Shape>()

        // list of polygon points to make a house shape
        val housePoints = listOf(
            Point2D(40.0, 0.0),
            Point2D(80.0, 40.0),
            Point2D(80.0, 100.0),
            Point2D(0.0, 100.0),
            Point2D(0.0, 40.0),
        )
        // can adjust model geometry directly too
        // (this is equivalent to model transforms below)
//            .map { p -> Point2D(p.x -40.0, p.y - 50.0)}

        //demo1: model-to-world transforms using full matrix definition
        val poly1 = Polygon(housePoints)

        val demo1 = {

            with(poly1) {
                fill = null
                stroke = Color.RED
                strokeWidth = 5.0
                displayList.add(this)
            }

            // model transform
//            poly1.translateX = -40.0
//            poly1.translateY = -50.0

            // translate
            val tx = 100.0
            val ty = 180.0
            poly1.concatTransform(
                Transform2D(
                    1.0, 0.0, tx,
                    0.0, 1.0, ty,
                    0.0, 0.0, 1.0,
                )
            )

            // rotate
            val theta = radians(-45.0)
            poly1.concatTransform(
                Transform2D(
                    cos(theta), -sin(theta), 0.0,
                    sin(theta), cos(theta), 0.0,
                    0.0, 0.0, 1.0,
                )
            )

            // uniform scale
            val s = 1.5
            poly1.concatTransform(
                Transform2D(
                    s, 0.0, 0.0,
                    0.0, s, 0.0,
                    0.0, 0.0, 1.0,
                )
            )
        }

        // model-to-world transforms using transform matrix helper
        val demo2 = {

            val poly2 = Polygon(housePoints)
            with(poly2) {
                fill = null
                stroke = Color.BLUE
                strokeWidth = 5.0
                displayList.add(this)
            }

            // model transform (centres the house on 0,0)
//            poly2.translateX = -40.0
//            poly2.translateY = -50.0

            // model-to-world transforms
            poly2.concatTransform(translate(100.0, 180.0))
            poly2.concatTransform(rotate(radians(-45.0)))
            poly2.concatTransform(scale(1.5))
        }

        // turn demos on and off here
        demo1()
//        demo2()

        // create a canvas to draw the shapes
        val canvas = Canvas(500.0, 500.0)

        // render all shapes to canvas
        fun renderDisplayList() {
            val gc = canvas.graphicsContext2D
            // reset transform to identity
            gc.transform = Affine()
            // clear background
            gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
            // shift 0,0 origin down and to the right
            gc.translate(100.0, 100.0)

            // draw the grid for reference
            gc.stroke = Color.LIGHTGRAY
            gc.lineWidth = 1.0
            gc.grid(0.0, 0.0, 300.0, 10.0)

            // display all our shapes
            for (s in displayList) {
                (s as Polygon).debugDraw(gc)
                s.draw(gc)
            }
        }

        // setup a hit-test test event
//        stage.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
//            poly1.rotate = radians((e.x/ stage.width) * 360.0)
//            renderDisplayList()
//        }

        // first render
        renderDisplayList()

        val scene = Scene(StackPane(canvas), canvas.width, canvas.height)
        stage.title = "Transforms/Shape"
        stage.scene = scene
        stage.show()
    }
}