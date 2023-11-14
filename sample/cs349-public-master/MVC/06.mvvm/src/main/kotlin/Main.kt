import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage) {
        val model = Model()
        val viewModel = ViewModel(model)
        val root = View(viewModel)

        // display all
        stage.scene = Scene(root)
        stage.show()
    }
}