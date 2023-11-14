import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.text.DecimalFormat

class FPS : Application() {
    var frameCount = 0L
    var currentTime = 0L
    var startTime = System.currentTimeMillis()-1
    var elapsedTime = 0.0
        get() {
            return ((currentTime - startTime) / 1000.0)
        }
    val decimalFormat = DecimalFormat("#.##")

    val frameCaption = Label("Frames: ")
    val frameValue = Label("0")
    val timeCaption = Label("Elapsed: ")
    val timeValue = Label("0.0")
    val fpsCaption = Label("FPS: ")
    val fpsValue = Label("0.0")

    val timer: AnimationTimer = object : AnimationTimer() {
        override fun handle(now: Long) {
            frameCount++
            currentTime = System.currentTimeMillis()
            updateValues()
        }
    }

    fun updateValues() {
        frameValue.text = frameCount.toString()
        timeValue.text = decimalFormat.format(elapsedTime)
        fpsValue.text = decimalFormat.format(frameCount.toFloat() / elapsedTime.toFloat()).toString()
    }

    override fun start(stage: Stage?) {
        val frameLayout = HBox(frameCaption, frameValue)
        val timeLayout = HBox(timeCaption, timeValue)
        val fpsLayout = HBox(fpsCaption, fpsValue)

        val root = VBox(frameLayout, timeLayout, fpsLayout)
        root.padding = Insets(10.0,10.0,10.0,10.0)
        root.spacing = 1.3

        stage?.scene = Scene(root)
        stage?.title = "FPS"
        stage?.width = 150.0
        stage?.height = 125.0
        stage?.isResizable = false
        stage?.setOnCloseRequest { System.exit(1) }
        stage?.show()

        timer.start()
    }
}