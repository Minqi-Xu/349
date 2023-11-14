import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage

/*
* Transform4
* Draw using built-in shape classes.
*/

class Main : Application() {
    private val width = 500.0
    private val height = 500.0

    override fun start(stage: Stage) {
        // Shapes to draw
        val shape1 = Rectangle(100.0, 100.0, Color.BLUE)
        shape1.translateX = 0.0 // works like setTranslateProperty()
        shape1.translateY = 0.0
        shape1.rotate = 15.0

        val shape2 = Rectangle(100.0, 100.0, Color.YELLOW)
        shape2.translateX = 50.0
        shape2.translateY = 50.0
        shape2.rotate = 15.0

        val shape3 = Rectangle(100.0, 100.0, Color.BLUEVIOLET)
        shape3.translateX = 100.0
        shape3.translateY = 100.0
        shape3.rotate = 15.0

        val shape4 = Rectangle(100.0, 100.0, Color.CHOCOLATE)
        shape4.translateX = 150.0
        shape4.translateY = 150.0
        shape4.rotate = 15.0

        // OPTION A: Just add them without transformations

//        Group root = new Group();
//        root.getChildren().add(shape1);
//        root.getChildren().add(shape2);
//        root.getChildren().add(shape3);
//        root.getChildren().add(shape4);
//        Scene scene = new Scene(root, screen_width, screen_height);

        // OPTION B: Transform each shape and NEST a second group
        // Notice the the second group inherits transforms from the parent.

        // Create a top-level group
        // Transformations in a group apply to all of its children
        val root1 = Group()
        root1.rotate = 15.0
        root1.children.add(shape1)
        root1.children.add(shape2)

        // Create a second level group
        // It inherits its parents transformations and adds its own
        val root2 = Group()
        root2.scaleX = 0.5
        root2.scaleY = 0.5
        root2.rotate = 15.0
        root2.translateX = 50.0
        root2.translateY = 50.0
        root2.children.add(shape3)
        root2.children.add(shape4)

        // Create a scene graph holding the group
        root1.children.add(root2)
        val scene = Scene(root1, width, height)

        // Add the scene to the stage and display it
        stage.scene = scene
        stage.show()
    }
}