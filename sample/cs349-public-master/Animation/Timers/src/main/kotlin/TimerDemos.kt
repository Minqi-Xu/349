import javafx.animation.*
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.util.Duration
import java.util.*


fun pauseTransitionDemo(aniScene: AnimatatableScene) {
    // create timer
    val timer = PauseTransition(Duration.millis(1000.0/60))
    // set timer finished event
    timer.onFinished = EventHandler { e: ActionEvent ->
        aniScene.x += 1.0       // animate parameter
        aniScene.draw()         // redraw updated scene
        timer.playFromStart()   // restart timer
    }
    // start timer
    timer.play()
}

fun timelineDemo(aniScene: AnimatatableScene) {
    // create Timeline timer with single "keyframe"
    val timer = Timeline(
        KeyFrame(Duration.millis(1000.0/60), { e ->
            aniScene.x += 1.0       // animate parameter
            aniScene.draw()         // redraw updated scene
        })
    )
    // set timer to repeat forever
    timer.cycleCount = Animation.INDEFINITE
    // start timer
    timer.play()
}

fun animationTimerDemo(aniScene: AnimatatableScene) {

    // create timer using JavaFX 60FPS execution thread
    val timer: AnimationTimer = object : AnimationTimer() {
        override fun handle(now: Long) {
            aniScene.x += 1.0       // animate parameter
            aniScene.draw()         // redraw updated scene
        }
    }
    // start timer
    timer.start()
}

fun javaTimerDemo1(aniScene: AnimatatableScene) {
    // create timer
    val timer = Timer()

    // schedule a task to repeat
    timer.scheduleAtFixedRate(
        // WARNING! This task is NOT executed on the JavaFX thread!
        object : TimerTask() {
            override fun run() {
                aniScene.x += 1.0       // animate parameter
                aniScene.draw()         // redraw updated scene
            }
        },
        0, 1000/60
    )
}

fun javaTimerDemo2(aniScene: AnimatatableScene) {
    // create timer
    val timer = Timer()

    // schedule a task to repeat
    timer.scheduleAtFixedRate(

        object : TimerTask() {
            override fun run() {
                // run the code on the JavaFX thread
                Platform.runLater {
                    aniScene.x += 1.0       // animate parameter
                    aniScene.draw()         // redraw updated scene
                }
            }
        },
        0, 1000/60
    )
}