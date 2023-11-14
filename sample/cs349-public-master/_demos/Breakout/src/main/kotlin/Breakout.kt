import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.stage.Stage
import java.util.*
import kotlin.system.exitProcess

/*
 * Mini, unoptimized, breakout for CS 349
 * (c) Jeff Avery 2021
 */

class Main : Application() {
    var width = 300.0
    var height = 300.0
    var radius = 20.0
    var dx = 0f
    var dy = 0f

    internal enum class STATE { STOP, RUN }
    private var gameState = STATE.RUN

    internal enum class DIRECTION {LEFT, RIGHT, NONE}
    private var paddleDirection = DIRECTION.NONE
    var paddleSpeed = 2.0f

    override fun start(stage: Stage) {
        // random drift for the ball
        val rand = Random()
        dx = -2 + rand.nextFloat() * 2
        dy = 2 + rand.nextFloat()

        // create ball, paddle
        val ball = Circle((width / 2), 25.0, radius)
        ball.fill = Color.BLUE
        val paddle = Rectangle(75.0, 15.0)
        paddle.x = (width / 2)
        paddle.y = (height - 50)

        val gameOver = Label("Game Over!")
        gameOver.font = Font.font("Helvetica", 18.0)
        gameOver.layoutX = (width / 2 - 50)
        gameOver.layoutY = (height / 2 - 100)
        gameOver.isVisible = false

        // add everything to the group and the scene
        val root = Group(ball, paddle, gameOver)
        val scene = Scene(root)

        // the timer drives all of the movement
        // reposition objects in the timer's handle event
        val timer: AnimationTimer = object : AnimationTimer() {
            override fun handle(now: Long) {
                // move ball
                ball.centerX = ball.centerX + dx
                ball.centerY = ball.centerY + dy

                // if it strikes the sides or top of the screen, change direction
                if (ball.centerX < radius || ball.centerX > width - radius) dx *= -1.0f
                if (ball.centerY < radius) dy *= -1.0f

                // if it strikes the bottom, game over!
                if (ball.centerY > height - radius - 25) {
                    gameState = STATE.STOP
                    gameOver.isVisible = true
                    this.stop()
                    return
                }

                // if it strikes the paddle, change direction and speed up x movement
                if (paddle.contains(ball.centerX, ball.centerY + radius)) {
                    dy *= -1.0f
                    if (paddleDirection == DIRECTION.LEFT) dx -= 0.1f
                    if (paddleDirection == DIRECTION.RIGHT) dx += 0.1f
                }

                // move the paddle
                // doing this here ensures that it's updated smoothly
                if (paddleDirection == DIRECTION.LEFT) paddle.x = paddle.x - paddleSpeed
                if (paddleDirection == DIRECTION.RIGHT) paddle.x = paddle.x + paddleSpeed
            }
        }
        timer.start()

        // use keypress to determine paddle direction
        scene.onKeyPressed = EventHandler { event: KeyEvent ->
            when (event.code) {
                KeyCode.LEFT -> paddleDirection = DIRECTION.LEFT
                KeyCode.RIGHT -> paddleDirection = DIRECTION.RIGHT
                KeyCode.Q -> exitProcess(0)
            }
        }
        scene.onKeyReleased = EventHandler {
            paddleDirection = DIRECTION.NONE
        }

        // show the scene
        stage.scene = scene
        stage.isResizable = false
        stage.title = "Mini-Breakout"
        stage.width = width
        stage.height = height
        stage.onCloseRequest = EventHandler {
            exitProcess(0)
        }
        stage.show()
    }
}