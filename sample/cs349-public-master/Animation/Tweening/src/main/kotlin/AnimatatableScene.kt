import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.scene.text.Font
import kotlin.math.round

// very simple interface for these demos
interface AnimatatableScene {
    abstract var x: Double
    fun draw() { }
}

// animatable scene using Canvas and GC drawing
// x position of a RED ball can be animated
class CanvasScene: AnimatatableScene,
    Canvas(WIDTH, HEIGHT) {

    // parameter to animate
    override var x: Double = 25.0

    override fun draw() {
        val gc = graphicsContext2D

        // clear background
        gc.clearRect(0.0, 0.0, width, height)

        // draw red ball
        gc.fill = Color.RED
        gc.fillOval(x - 25.0, HEIGHT/2 - 25.0, 50.0, 50.0)
    }
}

// animatable scene using a Shape
// x position of a BLUE ball can be animated
class ShapeScene: AnimatatableScene, Pane() {

    val ball = Ellipse(25.0, 25.0)

    // parameter to animate
    override var x: Double
        get() = ball.centerX
        set(value) { ball.centerX = value }

    init {
        prefWidth = WIDTH
        prefHeight = HEIGHT
        // set starting properties of ball
        ball.fill = Color.BLUE
        x = 0.0
        ball.centerY = HEIGHT / 2
        // add it to the scene
        children.add(ball)
    }

    /* doesn't need draw: JavaFX redraws scene graph 60 FPS */
}

// animatable scene changing properties on a widget (Node)
// the text property of a Label is animated
class WidgetScene: AnimatatableScene, StackPane() {

    // parameter to animate
    override var x: Double = 0.0

    val label = Label(round(x).toString())

    init {
        prefWidth = WIDTH
        prefHeight = HEIGHT
        label.font = Font(50.0)
        children.add(label)
    }

    override fun draw() {
        label.text = round(x).toString()
    }
}