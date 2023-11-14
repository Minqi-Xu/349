import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent

class MovableManager(parent: Node) {

    private var movingNode: Node? = null

    // the offset captured at start of drag
    private var offsetX = 0.0
    private var offsetY = 0.0

    init {

        // important that this is in bubble phase, not capture phase
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED) { e ->
            val node = movingNode
            if (node != null) {
                println("drop '$node'")
                movingNode = null
            }
        }

        parent.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
            val node = movingNode
            if (node != null) {
                node.translateX = e.sceneX + offsetX
                node.translateY = e.sceneY + offsetY
                // we don't want to drag the background too
                e.consume()
            }
        }
    }

    fun makeMovable(node: Node) {

        node.onMouseClicked = EventHandler { e ->
            if (movingNode == null) {
                println("click '$node'")
                this.movingNode = node
                offsetX = node.translateX - e.sceneX
                offsetY = node.translateY - e.sceneY
                // we don't want to drag the background too
                e.consume()
            }
        }
    }
}