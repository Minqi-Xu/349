import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.stage.Stage

/**
 * Demonstrates a drag-and-drop feature.
 */
class DragAndDrop : Application() {
    override fun start(stage: Stage) {
        stage.title = "Drag And Drop Demo"
        val root = Group()
        val scene = Scene(root, 400.0, 200.0)
        scene.fill = Color.LIGHTGREEN
        val source = Text(50.0, 100.0, "DRAG ME")
        source.scaleX = 2.0
        source.scaleY = 2.0
        val target = Text(250.0, 100.0, "DROP HERE")
        target.scaleX = 2.0
        target.scaleY = 2.0
        source.onDragDetected = EventHandler { event ->
            println("onDragDetected")

            /* allow any transfer mode */
            val db = source.startDragAndDrop(*TransferMode.ANY)

            /* put a string on dragboard */
            val content = ClipboardContent()
            content.putString(source.text)
            db.setContent(content)
            event.consume()
        }

        /* the drag over the target */
        target.onDragOver = EventHandler { event ->
            println("onDragOver")

            /* accept it only if it is  not dragged from the same node
             * and if it has a string data
             */
            if (event.gestureSource !== target && event.dragboard.hasString() ) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
            }
            event.consume()
        }

        /* the drag-and-drop gesture entered the target */
        target.onDragEntered = EventHandler { event ->
            println("onDragEntered")
            /* show to the user that it is an actual gesture target */
            if (event.gestureSource !== target && event.dragboard.hasString()) {
                target.fill = Color.GREEN
            }
            event.consume()
        }

        /* mouse moved away, remove the graphical cues */
        target.onDragExited = EventHandler { event ->
            target.fill = Color.BLACK
            event.consume()
        }

        /* data dropped */
        target.onDragDropped = EventHandler { event ->
            println("onDragDropped")

            /* if there is a string data on dragboard, read it and use it */
            val db = event.dragboard
            var success = false
            if (db.hasString()) {
                target.text = db.string
                success = true
            }
            /* let the source know whether the string was successfully
             * transferred and used
             */
            event.isDropCompleted = success
            event.consume()
        }

        /* the drag-and-drop gesture ended */
        source.onDragDone = EventHandler { event ->
            println("onDragDone")
            /* if the data was successfully moved, clear it */
            if (event.transferMode == TransferMode.MOVE) {
                source.text = ""
            }
            event.consume()
        }
        root.children.add(source)
        root.children.add(target)
        stage.scene = scene
        stage.show()
    }
}