import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage

// MVC1 Separate View and Controller (not typically done, but example of "pure" MVC)
// A simple MVC example inspired by Joseph Mack, http://www.austintek.com/mvc/
// This version uses MVC: two views coordinated with the observer pattern and a separate controller.
class MVC1 : Application() {
    @Throws(Exception::class)
    override fun start(stage: Stage) {
        // window name
        stage.title = this.javaClass.name

        // create and initialize the Model to hold our counter
        val model = Model()

        // create the Controller, and tell it about Model
        // the controller will handle input and pass requests to the model
        val controller = Controller(model)

        // create each view, and tell them about model and controller
        // the views will register themselves with these components
        // and handle displaying the data from the model
        val view1 = View1(model, controller)
        val view2 = View2(model, controller)
        val vbox = VBox()
        vbox.children.add(view1) // top-view
        vbox.children.add(view2) // bottom-view

        // Add grid to a scene (and the scene to the stage)
        val scene = Scene(vbox, 200.0, 200.0)
        stage.scene = scene
        stage.show()
    }
}