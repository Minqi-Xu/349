import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.animation.*
import javafx.util.Duration

class Main : Application() {
    override fun start(stage: Stage) {

        val button = Button("Click")

        // Scale Transition
        val scaleTransition = ScaleTransition(Duration.seconds(1.5), button)
        scaleTransition.byX = 2.5
        scaleTransition.byY = 2.5

        // Rotation Transition
        val rotateTransition = RotateTransition(Duration.seconds(1.5), button)
        rotateTransition.byAngle = 360.0

        // Create sequence of rotate, then scale
        val transition1 =
            SequentialTransition(scaleTransition, rotateTransition)
//            ParallelTransition(scaleTransition, rotateTransition)
        transition1.play()

        // Fade Animation (on Button click)
        val transition2 = FadeTransition(Duration.seconds(1.0), button)
        transition2.fromValue = 1.0
        transition2.toValue = 0.0

        button.setOnAction { transition2.play() }

        val scene = Scene(StackPane(button), 300.0, 300.0)
        stage.title = "Animation/Sequence"
        stage.scene = scene
        stage.show()
    }
}