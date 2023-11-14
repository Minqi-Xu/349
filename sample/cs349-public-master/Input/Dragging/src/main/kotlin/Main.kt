import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.*

import javafx.scene.text.Font

class Main : Application() {

    val width = 400.0
    val height = 400.0

    override fun start(stage: Stage) {

        val root = Pane()
        root.background = simpleFill(Color.WHITESMOKE)

        // region draggable nodes

        // blue circle that can be draged
        val circle = Circle(75.0, 200.0, 50.0)
        with (circle) {
            stroke = Color.BLACK
            strokeWidth = 1.0
            fill = Color.BLUE
            makeDraggable(this)
        }

        val label = Label("DRAG")
        with (label) {
            font = Font(40.0)
            background = simpleFill(Color.WHITE)
            makeDraggable(this)
        }

        // make a draggable handle kind of button
        val handle = Button("")
        with (handle) {
            // use 0 font so the button height can be resized small
            font = Font(0.0)
            prefWidth = 16.0
            prefHeight = 16.0
            translateX = 100.0
            translateY = 100.0
            makeDraggable(this)
        }

        root.children.addAll(label, circle, handle)

        // try making the whole pane draggable too
//        makeDraggable(root)

        // endregion

        // region movable objects

        // create the object to manage the movable object states
        val mover = MovableManager(root)

        // red circle that cna be moved with a click
        val circle2 = Circle(325.0, 200.0, 50.0)
        with (circle2) {
            stroke = Color.BLACK
            strokeWidth = 1.0
            fill = Color.RED
            mover.makeMovable(this)
        }

        val label2 = Label("MOVE")
        with (label2) {
            translateX = 225.0
            translateY = 50.0
            font = Font(40.0)
            background = simpleFill(Color.WHITE)
            mover.makeMovable(this)
        }

        root.children.addAll(circle2, label2)

        // endregion

        val scene = Scene(root, width, height)
        stage.title = "Input/Dragging"
        stage.scene = scene
        stage.show()

//        startOnScreen2(stage)
    }
}

/*
 * useful for quickly assigning a solid fill colour
 */
fun simpleFill(c: Color): Background {
    return Background(BackgroundFill(c, null, null))
}
