import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import kotlin.system.exitProcess

// Use Event Filters to process mouse-clicks on the capture phase.
class EventFilters : Application() {

    fun toggleColour(current: Paint?, colour1: Color, colour2: Color): Color {
        return (if (current == colour1) colour2 else colour1)
    }

    override fun start(stage: Stage) {

        val group = StackPane()
        val scene = Scene(group, 400.0, 300.0)

        // setup background
        scene.fill = Color.GRAY
        val sceneHandler =
            EventHandler<MouseEvent> { event ->
                println("Scene clicked (${event.x}, ${event.y})")
                scene.fill = toggleColour(scene.fill, Color.GRAY, Color.DARKGRAY)
//               event.consume()
            }
        // add the handler as an EventFilter, which is fired during the CAPTURE (down) phase
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, sceneHandler)

        // add foreground shape
        val rectangle = Rectangle(125.0, 150.0, Color.YELLOW)
        val rectangleHandler =
            EventHandler<MouseEvent> { event ->
                println("Rect clicked (${event.x}, ${event.y})")
                rectangle.fill = toggleColour(rectangle.fill, Color.YELLOW, Color.DARKGOLDENROD)
//                event.consume()
            }
        // add the handler as an EventFilter, which is fired during the CAPTURE (down) phase
        rectangle.addEventFilter(MouseEvent.MOUSE_CLICKED, rectangleHandler)

        // add to the scene and display
        group.children.add(rectangle)
        stage.onCloseRequest = EventHandler {
            println("Quitting")
            exitProcess(0)
        }
        stage.title = "EventHandlers1: Event Filters"
        stage.scene = scene
        stage.show()
    }
}