import javafx.application.Application
import javafx.geometry.Point2D
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color

import javafx.stage.Stage
import kotlin.random.Random

class Main : Application() {

    override fun start(stage: Stage) {

        // create a canvas to draw the shapes
        val canvas = Canvas(400.0, 400.0)

        // helper function
        fun randomPoint2D(): Point2D {
            val m = 50.0 // keep points inside this margin
            return Point2D(
                Random.nextDouble(m, canvas.width - m),
                Random.nextDouble(m, canvas.height - m))
        }

        // initial setup of points
        var P0 = randomPoint2D()
        var P1 = randomPoint2D()
        var M = Point2D(0.0,0.0)
        var Q = Point2D(0.0,0.0)
        var QInf = Point2D(0.0,0.0)

        fun render() {
            val gc = canvas.graphicsContext2D
            gc.clearRect(0.0, 0.0, canvas.width, canvas.height)

            val pSize = 10.0

            // draw projected line
            gc.stroke = Color.LIGHTGRAY
            gc.lineWidth = 1.0
            val P0Far = P0.add(P0.subtract(P1).multiply(100.0))
            val P1Far = P1.add(P1.subtract(P0).multiply(100.0))
            gc.strokeLine(P0Far.x, P0Far.y, P1Far.x, P1Far.y)

            // draw line segment and points
            gc.stroke = Color.BLACK
            gc.lineWidth = 3.0
            gc.strokeLine(P0.x, P0.y, P1.x, P1.y)
            gc.fill = Color.BLACK
            gc.fillOval(P1.x - pSize/2 , P1.y - pSize/2, pSize, pSize)
            gc.fillOval(P0.x - pSize/2 , P0.y - pSize/2, pSize, pSize)

            // draw mouse point
            gc.stroke = Color.BLUE
            gc.lineWidth = 2.0
            gc.strokeOval(M.x - pSize , M.y - pSize, pSize * 2, pSize * 2)

            // draw closest point on infinte line
            gc.stroke = Color.LIGHTGRAY
            gc.lineWidth = 2.0
            gc.strokeOval(QInf.x - pSize , QInf.y - pSize, pSize * 2, pSize * 2)

            // draw closest point on line segment
            gc.stroke = Color.RED
            gc.lineWidth = 2.0
            gc.strokeOval(Q.x - pSize , Q.y - pSize, pSize * 2, pSize * 2)
        }

        // setup a hit-test test event
        stage.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
            M = Point2D(e.x, e.y)
            Q = closestPoint(M, P0, P1)
            // just for this demo, find point on infinite line too
            QInf = closestPoint(M, P0, P1, false)
            render()
        }

        stage.addEventFilter(MouseEvent.MOUSE_CLICKED) { e ->
            P0 = randomPoint2D()
            P1 = randomPoint2D()
            M = Point2D(e.x, e.y)
            Q = closestPoint(M, P0, P1)
            // just for this demo, find point on infinite line too
            QInf = closestPoint(M, P0, P1, false)
            render()
        }

        // first render
        render()

        val scene = Scene(Pane(canvas), 400.0, 400.0)
//        scene.setCursor(Cursor.NONE)
        stage.title = "Hit-Testing/ClosestPoint"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }


}