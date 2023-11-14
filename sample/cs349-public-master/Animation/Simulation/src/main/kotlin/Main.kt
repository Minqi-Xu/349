import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.stage.Stage
import kotlin.random.Random

class Main : Application() {

    private val ball = Circle()
    private var dx = 1.0
    private var dy = 1.5

    private val scene = Scene(Pane(ball), 300.0, 300.0)

    override fun start(stage: Stage) {

        with (ball) {
            radius = 25.0
            // randomize starting position
            centerX = Random.nextDouble(radius, scene.width - radius)
            centerY = Random.nextDouble(radius, scene.height - radius)
            fill = Color.GREEN
        }

        // timer ticks every time we want to advance a frame
        // An AnimationTimer runs at 60 FPS, standard update rate for JavaFX
        val timer = object: AnimationTimer() {
            override fun handle(now: Long) {
                runSimulation()
            }
        }

        // note timer will stop when the program terminates
        timer.start()

        stage.title = "Animation/Simulation"
        stage.scene = scene
        // could make it resizable, but then need more logic to handle ball outside
        // scene when resized smaller
        stage.isResizable = false
        stage.show()
    }

    fun runSimulation() {

        // if we hit the edge of the window, change direction
        if (ball.centerX < ball.radius ||
            ball.centerX > scene.width - ball.radius) {
            dx *= -1.0
        }
        if (ball.centerY < ball.radius ||
            ball.centerY > scene.height - ball.radius) {
            dy *= -1.0
        }

        // update the ball position
        ball.centerX += dx
        ball.centerY += dy
    }
}