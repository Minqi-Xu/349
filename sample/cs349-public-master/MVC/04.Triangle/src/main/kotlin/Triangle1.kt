import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class Triangle1 : Application() {
    @Throws(Exception::class)
    override fun start(stage: Stage) {
        // create and initialize the Model to hold our counter
        val model = Model()

        // create a view, and tell it about the model
        val view = TextView(model)

        // add view to the scene (and the scene to the stage)
        val scene = Scene(view, 300.0, 150.0)
        stage.scene = scene
        stage.title = "Triangle1"
        stage.show()
    }
}