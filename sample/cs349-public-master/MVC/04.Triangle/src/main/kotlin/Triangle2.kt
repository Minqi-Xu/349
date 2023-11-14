import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Triangle2 : Application() {
    @Throws(Exception::class)
    override fun start(stage: Stage) {
        // create and initialize the Model to hold our counter
        val model = Model()

        // create and register views
        val view1 = TextView(model)
        val view2 = ButtonView(model)
        val view3 = SliderView(model)

        // setup grid with views
        val group = VBox()
        group.spacing = 15.0
        group.padding = Insets(15.0)
        group.children.add(view1)
        group.children.add(view2)
        group.children.add(view3)

        // add grid to the scene and stage
        val scene = Scene(group, 300.0, 400.0)
        stage.scene = scene
        stage.title = "Triangle2"
        stage.isResizable = false
        stage.show()
    }
}