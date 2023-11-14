import javafx.animation.*
import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Ellipse
import javafx.util.Duration

const val WIDTH = 300.0
const val HEIGHT = 300.0

class Main : Application() {

    override fun start(stage: Stage) {

        // create our animated scene
        val aniScene = Pane()
        with (aniScene) {
            prefWidth = WIDTH
            prefHeight = HEIGHT
        }

        val ball = Ellipse(25.0, 25.0)
        with (ball) {
            fill = Color.BLUE
            centerX = 50.0
            centerY = aniScene.prefHeight/2
        }

        aniScene.children.add(ball)

        // create a transition to move ball from left to right
        val transition = TranslateTransition(Duration.seconds(1.0), ball)
        transition.interpolator = Interpolator.EASE_IN
        transition.fromX = 0.0
        transition.toX = WIDTH - 100.0
//        transition.byX = 100.0

        // transition a fill colour
//        val transition = FillTransition(Duration.seconds(1.0), ball)
//        transition.fromValue = Color.BLUE
//        transition.toValue = Color.RED

        // button to start the animation
        val button = Button("Start")
        button.translateY = 100.0
        button.setOnAction { transition.play() }

        val scene = Scene(StackPane(aniScene, button), WIDTH, HEIGHT)
        stage.title = "Animation/Transitions"
        stage.isResizable = false
        stage.scene = scene
        stage.show()
    }
}
