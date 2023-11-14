import javafx.application.Application
import javafx.geometry.Point2D
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.scene.transform.Affine

import javafx.stage.Stage
import kotlin.math.*

class Main : Application() {

    override fun start(stage: Stage) {

        val pane = Pane()
        with (pane) {
            // shift 0,0 origin down and to the right to match canvas
            translateX = 100.0
            translateY = 100.0
        }

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

        val housePointsFlat = housePoints.flatMap { p -> listOf(p.x, p.y) }.toDoubleArray()

        //demo1: model-to-world transforms using full matrix definition
        val demo1 = {
            val poly1 = Polygon(*housePointsFlat)
            with(poly1) {
                fill = null
                stroke = Color.RED
                strokeWidth = 5.0
                pane.children.add(this)
            }

            // model transform
//            poly1.translateX = -40.0
//            poly1.translateY = -50.0

            var transform = Affine()

            // translate
            val tx = 100.0
            val ty = 180.0
            transform.append(
                Affine(
                    1.0, 0.0, tx,
                    0.0, 1.0, ty
                )
            )

            // rotate
            val theta = radians(-45.0)
            transform.append(
                Affine(
                    cos(theta), -sin(theta), 0.0,
                    sin(theta), cos(theta), 0.0
                )
            )

            // uniform scale
            val s = 1.5
            transform.append(
                Affine(
                    s, 0.0, 0.0,
                    0.0, s, 0.0
                )
            )

            // concatenate the transform to the Shape transform
            poly1.transforms.add(transform)
        }

        // model-to-world transforms using transform matrix helper
        val demo2 = {

            val poly2 = Polygon(*housePointsFlat)
            with(poly2) {
                fill = null
                stroke = Color.BLUE
                strokeWidth = 5.0
                pane.children.add(this)
            }

            // model transform (centres the house on 0,0)
            poly2.translateX = -40.0
            poly2.translateY = -50.0

            var transform = Affine()

            // model-to-world transforms
            transform.appendTranslation(100.0, 180.0)
            transform.appendRotation(-45.0)
            transform.appendScale(1.5, 1.5)

            poly2.transforms.add(transform)
         }

        // turn demos on and off here
        demo1()
//        demo2()

        // create a canvas to draw the shapes
        val canvas = Canvas(500.0, 500.0)

        // render grid to canvas
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
        }

        // setup a hit-test test event
        stage.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
            renderDisplayList()
        }

        // first render
        renderDisplayList()

        val scene = Scene(StackPane(canvas, pane), canvas.width, canvas.height)
        stage.title = "Transforms/Shape"
        stage.scene = scene
        stage.show()
    }
}

fun radians(degrees: Double): Double {
    return degrees * (PI / 180.0)
}