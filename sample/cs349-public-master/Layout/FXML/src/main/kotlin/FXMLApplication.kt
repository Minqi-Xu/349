import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class FXMLApplication : Application() {
    private val model: Model = Model()

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        // this instantiates both the FXMLApplication and FXMLController
        val fxmlLoader = FXMLLoader()
        val root = fxmlLoader.load<Parent>(javaClass.getResource("BasicFXML.fxml")?.openStream())
        val controller: FXMLController = fxmlLoader.getController<Any>() as FXMLController
        controller.setModel(model)

        // show stage
        val scene = Scene(root)
        stage.scene = scene
        stage.show()
    }
}