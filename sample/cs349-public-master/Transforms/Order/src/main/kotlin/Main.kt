import javafx.application.Application
import javafx.geometry.Point2D
import javafx.geometry.VPos
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment

import javafx.stage.Stage
import kotlin.random.Random

class Main : Application() {

    override fun start(stage: Stage) {

        // create a canvas
        val canvas = Canvas(500.0, 500.0)

        // set it up with a nice centred grid and scale it up
        with (canvas) {
            // make it larger for the demos
            scaleX = 3.0
            scaleY = 3.0

            // clear bg and draw a grid
            with (graphicsContext2D) {
                clearRect(0.0, 0.0, canvas.width, canvas.height)
                // move 0,0 down a bit so we have a margin all around
                translate(200.0, 200.0)
                // draw the 100 x 100 grid
                lineWidth = 0.5
                stroke = Color.LIGHTGRAY
                grid(0.0, 0.0, 100.0, 10.0)
            }
        }

        val gc = canvas.graphicsContext2D

        // choose demo
        when (4) {
            1 -> orderDemo(gc)
            2 -> cumulativeDemo(gc)
            3 -> saveDemo(gc)
            4 -> rotateBarDemo(gc)
        }

        val scene = Scene(StackPane(canvas), 400.0, 400.0)
        stage.title = "Transforms/Order"
        stage.scene = scene
        stage.isResizable = true
        stage.show()
    }

    fun demoStart(gc: GraphicsContext) {

        // before transformation
        gc.bar(30.0, 40.0, 70.0, 40.0, Color.GRAY)

    }

    fun orderDemo(gc: GraphicsContext) {

        // before transformation
        // draw 30 unit long bar with left end at origin
        gc.bar(20.0, 10.0, 50.0, 10.0, Color.GRAY)

        val demo = "RST"

        with (gc) {
            fill = Color.BLACK
            textBaseline = VPos.CENTER
            textAlign = TextAlignment.CENTER
            font = Font(12.0)
            fillText("$demo", 50.0, -10.0)
        }

        when (demo) {

            "TRS" -> {
                gc.translate(20.0, 10.0)
                gc.rotate(30.0)
                gc.scale(1.5, 1.5)
                gc.bar(20.0, 10.0, 50.0, 10.0, Color.RED)
            }

            "TSR" -> {
                gc.translate(20.0, 10.0)
                gc.scale(1.5, 1.5)
                gc.rotate(30.0)
                gc.bar(20.0, 10.0, 50.0, 10.0, Color.ORANGE)
            }

            "SRT" -> {
                gc.scale(1.5, 1.5)
                gc.rotate(30.0)
                gc.translate(20.0, 10.0)
                gc.bar(20.0, 10.0, 50.0, 10.0, Color.GREEN)
            }

            "STR" -> {
                gc.scale(1.5, 1.5)
                gc.translate(20.0, 10.0)
                gc.rotate(30.0)
                gc.bar(20.0, 10.0, 50.0, 10.0, Color.CADETBLUE)
            }

            "RTS" -> {
                gc.rotate(30.0)
                gc.translate(20.0, 10.0)
                gc.scale(1.5, 1.5)
                gc.bar(20.0, 10.0, 50.0, 10.0, Color.BLUE)
            }

            "RST" -> {
                gc.rotate(30.0)
                gc.scale(1.5, 1.5)
                gc.translate(20.0, 10.0)
                gc.bar(20.0, 10.0, 50.0, 10.0, Color.VIOLET)
            }
        }


    }

    fun cumulativeDemo(gc: GraphicsContext) {

        // before transformation
        // draw 30 unit long bar with left end at origin
        gc.bar(0.0, 0.0, 20.0, 0.0, Color.GRAY)

        // transform and draw same bar
        gc.translate(50.0, 10.0)  // 1. move to origin
        gc.rotate(90.0)
        gc.bar(0.0, 0.0, 20.0, 0.0, Color.BLUE)

        // transform more and draw same bar
        gc.translate(20.0, 0.0)
        gc.rotate(25.0)
        gc.bar(0.0, 0.0, 20.0, 0.0, Color.RED)

        // transform more and draw same bar
        gc.translate(20.0, 0.0)
        gc.rotate(25.0)
        gc.bar(0.0, 0.0, 20.0, 0.0, Color.GREEN)
    }

    fun saveDemo(gc: GraphicsContext) {

        // before transformation
        // draw 30 unit long bar with left end at origin
        gc.bar(0.0, 0.0, 20.0, 0.0, Color.GRAY)

        // transform and draw same bar
        gc.translate(50.0, 10.0)  // 1. move to origin
        gc.rotate(90.0)
        gc.bar(0.0, 0.0, 20.0, 0.0, Color.BLUE)

        // transform down for the two bars
        gc.translate(20.0, 0.0)

        // save the transform at this step
        val save = gc.transform

        // transform for RED bar
        gc.rotate(35.0)
        gc.bar(0.0, 0.0, 20.0, 0.0, Color.RED)

        // restore the transform to the saved state
        gc.transform = save

        // transform for GREEN bar
        gc.rotate(-35.0)
        gc.bar(0.0, 0.0, 20.0, 0.0, Color.GREEN)
    }

    fun rotateBarDemo(gc: GraphicsContext) {

        // before transformation
        // draw 40 unit long horiz bar with left end at 30, 40
        gc.bar(30.0, 40.0, 70.0, 40.0, Color.GRAY)

        // transform
        gc.translate(30.0, 40.0)    // 3. move back to position
        gc.rotate(30.0)           // 2. rotate
        gc.translate(-30.0, -40.0)  // 1. move to origin

        // draw bar after transformation
        gc.bar(30.0, 40.0, 70.0, 40.0, Color.BLUE)
    }
}