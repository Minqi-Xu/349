import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.layout.*

const val WIDTH = 300.0
const val HEIGHT = 300.0

class Main : Application() {

    override fun start(stage: Stage) {

        // IMPORTANT: animating LabelScene with javaTimerDemo1
        // will throw an exception due to UI threading

        // switch between different animatable scenes
        val aniScene =
            when (1) {
                1 -> CanvasScene()
                2 -> ShapeScene()
                3 -> WidgetScene()
                else -> CanvasScene()
            }

        // switch between different timers
        when(1) {
            1 -> pauseTransitionDemo(aniScene)
            2 -> timelineDemo(aniScene)
            3 -> animationTimerDemo(aniScene)
            4 -> javaTimerDemo1(aniScene)
            5 -> javaTimerDemo2(aniScene)
        }

        val scene = Scene(StackPane(aniScene), WIDTH, HEIGHT)
        stage.title = "Animation/Timers"
        stage.isResizable = false
        stage.scene = scene
        stage.show()
    }
}
