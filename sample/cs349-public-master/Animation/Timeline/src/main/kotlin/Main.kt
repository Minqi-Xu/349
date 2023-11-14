import javafx.animation.*
import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Ellipse
import javafx.util.Duration

const val WIDTH = 600.0
const val HEIGHT = 300.0

class Main : Application() {

    val ball = Ellipse(25.0, 25.0)

    override fun start(stage: Stage) {

        // animated scene
        val aniScene = Pane()
        with(aniScene) {
            prefWidth = WIDTH
            prefHeight = HEIGHT
        }

        // setup ball to animate
        with(ball) {
            fill = Color.BLUE
            centerX = 50.0
            centerY = aniScene.prefHeight / 2
        }

        aniScene.children.add(ball)

        val t = timeline1
        // other timeline properties
//        t.setAutoReverse(true)
//        t.setCycleCount(Animation.INDEFINITE)

        // button to start the keyframe animation
        val button = Button("Start")
        button.setOnAction { t.play() }
        button.translateY = 100.0

        val scene = Scene(StackPane(aniScene, button), WIDTH, HEIGHT)
        stage.title = "Animation/Timeline"
        stage.isResizable = false
        stage.scene = scene
        stage.show()
    }

    // region timeline1 Simple Timeline

    val timeline1 = Timeline(
        // first keyframe sets beginning of animation
        KeyFrame(
            Duration.ZERO,
            KeyValue(
                ball.centerXProperty(),
                50.0,
                Interpolator.EASE_BOTH
            )
        ),
        KeyFrame(
            Duration.seconds(1.0),
            KeyValue(
                ball.centerXProperty(),
                WIDTH - 50.0,
                Interpolator.EASE_BOTH
            )
        ),
    )

    // endregion

    // region timeline2 More Complex Timeline

    // animates ball x and y postion and colour with
    // 3 key frames and overlapping movement
    val timeline2 =  Timeline(
        KeyFrame(
            Duration.ZERO,
            KeyValue(
                ball.centerXProperty(),
                50.0,
                Interpolator.EASE_BOTH
            ),
            KeyValue(
                ball.centerYProperty(),
                150.0,
                Interpolator.EASE_BOTH
            ),
            KeyValue(
                ball.fillProperty(),
                Color.BLUE
            ),

        ),
        KeyFrame(
            Duration.seconds(0.75),
            KeyValue(
                ball.centerYProperty(),
                50.0,
                Interpolator.EASE_BOTH
            )
        ),
        KeyFrame(
            Duration.seconds(1.5),
            KeyValue(
                ball.centerXProperty(),
                WIDTH - 50.0,
                Interpolator.EASE_BOTH
            ),
            KeyValue(
                ball.centerYProperty(),
                150.0,
                Interpolator.EASE_BOTH
            ),
            KeyValue(
                ball.fillProperty(),
                Color.RED
            ),
        )
    )

    //endregion

}

