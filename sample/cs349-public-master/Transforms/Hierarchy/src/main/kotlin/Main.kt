import javafx.application.Application
import javafx.geometry.Point2D
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.transform.Affine
import javafx.stage.Screen

import javafx.stage.Stage

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
//        .map { p -> Point2D(p.x -40.0, p.y - 50.0)}

        // these all have exactly the same model geometry
        val biggestHouse = Polygon(housePoints)
        val bigHouse = Polygon(housePoints)
        val leftHouse = Polygon(housePoints)
        val rightHouse = Polygon(housePoints)

        // model-to-world transforms using transform matrix helper
        val demo = {

            with(biggestHouse) {
                fill = Color.WHITESMOKE
                // model transform
                translateX = -40.0
                translateY = -50.0
                scaleX = 2.0
                scaleY = 2.0
                // world transform
                concatTransform(translate(150.0, 150.0))
                // only this parent shape is added to display list
                displayList.add(this)
            }

           with(bigHouse) {
                fill = Color.LIGHTGREY
                // model transform
                translateX = -40.0
                translateY = -50.0
                // world transform
                concatTransform(translate(0.0, 20.0))
                concatTransform(rotate(radians(180.0)))
                // add to parent
                biggestHouse.addChild(this)
            }

            with(leftHouse) {
                fill = Color.RED
                translateX = -40.0
                translateY = -50.0
                concatTransform(translate(-20.0, 0.0))
                concatTransform(rotate(radians(-15.0)))
                concatTransform(scale(0.333))
                // add to parent
                bigHouse.addChild(this)
            }

            with(rightHouse) {
                fill = Color.BLUE
                translateX = -40.0
                translateY = -50.0
                concatTransform(translate(20.0, 0.0))
                concatTransform(rotate(radians(15.0)))
                concatTransform(scale(0.333))
                // add to parent
                bigHouse.addChild(this)
            }
        }

        // turn demos on and off here
        demo()

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
//                biggestHouse.debugDraw(gc)
                s.draw(gc)
            }
        }

        // save the base model-to-world transform so we can dynamically
        // add some additional transformations when interacting
        val btBiggestHouse = biggestHouse.transform
        val btBigHouse = bigHouse.transform
        val btLeftHouse = leftHouse.transform
        val btRightHouse = rightHouse.transform

        // interactive transform
        canvas.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
            val r = radians((e.y / stage.height) * 360.0)
            // subtract 240 to compensate for 50 grid offset and bigHouse translate transforms (40 + 150)
             val x = e.x - 240.0
            biggestHouse.transform = btBiggestHouse.multiply(translate(x, 0.0))
            bigHouse.transform = btBigHouse.multiply(rotate(r))
//            leftHouse.transform = btLeftHouse.multiply(rotate(-r))
//            rightHouse.transform = btRightHouse.multiply(rotate(-r))
            renderDisplayList()
        }

        // first render
        renderDisplayList()

        val scene = Scene(StackPane(canvas), canvas.width, canvas.height)
        stage.title = "Transforms/Hierarchy"
        stage.scene = scene
        stage.show()

        if (false) {
            // keep application window on secondary display
            val x = Screen.getScreens()[1].getVisualBounds().maxX;
            stage.x = x - 550.0
            stage.y = 50.0
        }
    }
}