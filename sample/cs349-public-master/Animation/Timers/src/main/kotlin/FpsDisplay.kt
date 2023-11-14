import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.text.Font
import java.text.DecimalFormat

class FpsDisplay : Label() {

    init {
        text = "?"
        padding = Insets(10.0)
        font = Font(20.0)
    }

    var lastFrameTime = System.currentTimeMillis()

    var fps = 0.0

    var startup = 10

    fun update() {
        val fpsFormatter = DecimalFormat("#.#")
        val diff = System.currentTimeMillis() - lastFrameTime
//        println(diff)
        val newFps = 1000.0 / diff
        lastFrameTime = System.currentTimeMillis()

        if (startup > 0) {
            startup--
            fps = newFps
        } else {
            val a = 0.05
            fps = (1 - a) * fps + a * newFps
        }
        text = fpsFormatter.format(fps)
    }
}