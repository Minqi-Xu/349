import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage) {
        val image = Image("java.png", 170.0, 170.0, false, false)
        val imageView = ImageView(image)

        val java = "${System.getProperty("java.vendor")} ${System.getProperty("java.version")}"
        val javaFX = "Gluon JavaFX ${System.getProperty("javafx.version")}"
        val label = Label(java + "\n" + javaFX)

        val box = VBox(imageView, label)
        VBox.setMargin(label, Insets(10.0))

        val scene = Scene(box, 175.0, 225.0)
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}