import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.text.Font
import javafx.stage.Stage
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Clock : Application() {
    override fun start(stage: Stage) {
        // setup time
        val time = Label()
        time.font = Font("Helvetica", 28.0)

        // timer fires every 1/60 seconds and updates time
        val dateFormat: DateFormat = SimpleDateFormat("hh:mm:ss")

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Platform.runLater() {
                    time.text = dateFormat.format(Calendar.getInstance().time)
                    println("Tick: ${time.text}")
                }
            }
        }, 0, 1000)

        // show scene & stage
        stage.scene = Scene(StackPane(time))
        stage.title = "Clock"
        stage.isResizable = false
        stage.width = 200.0
        stage.height = 100.0
        stage.show()
    }
}