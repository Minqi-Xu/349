import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.stage.Stage

// Draw using JavaFX shape classes
// These are built-in subclasses of javafx.scene.shape
class Main : Application() {
    override fun start(stage: Stage) {
        // Create a lots of shapes to display
        val rectangle = Rectangle(100.0, 100.0)
        rectangle.fill = Color.RED
        rectangle.x = 50.0
        rectangle.y = 50.0
        rectangle.onMouseClicked = EventHandler {
            println("Rectangle clicked")
        }
        val polygon = Polygon(250.0, 175.0, 350.0, 175.0, 350.0, 75.0, 300.0, 25.0, 250.0, 75.0)
        polygon.stroke = Color.LIGHTYELLOW
        polygon.fill = Color.BLUE
        polygon.onMouseClicked = EventHandler {
            println("Polygon clicked")
        }
        val circle = Circle(50.0)
        circle.fill = Color.YELLOW
        circle.centerX = 175.0
        circle.centerY = 325.0
        circle.onMouseClicked = EventHandler {
            println("Circle clicked")
        }
        val text = Text(50.0, 225.0, "The javafx.scene.shape package contains many shapes")
        text.textAlignment = TextAlignment.LEFT
        text.onMouseClicked = EventHandler {
            println("Text clicked")
        }
        text.onMouseClicked = EventHandler { mouseEvent: MouseEvent? ->
            println("Text clicked")
        }
        val line = Line(325.0, 275.0, 275.0, 450.0)
        line.fill = Color.GRAY
        line.strokeWidth = 10.0
        line.onMouseClicked = EventHandler {
            println("Line clicked")
        }
        val ellipse = Ellipse()
        ellipse.fill = Color.AQUA
        ellipse.centerX = 125.0
        ellipse.centerY = 425.0
        ellipse.radiusX = 50.0
        ellipse.radiusY = 25.0
        ellipse.onMouseClicked = EventHandler {
            println("Ellipse clicked")
        }

        // Create a group to hold everything
        val group = Group()
        group.children.add(rectangle)
        group.children.add(circle)
        group.children.add(line)
        group.children.add(text)
        group.children.add(polygon)
        group.children.add(ellipse)

        // Create a scene graph holding the group
        val scene = Scene(group, 400.0, 500.0)

        // Add the scene to the stage and display it
        stage.title = "Drawing Shapes"
        stage.scene = scene
        stage.show()
    }
}