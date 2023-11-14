import javafx.event.EventHandler
import javafx.scene.Node

fun makeDraggable(node: Node) {

    // the offset captured at start of drag
    var offsetX = 0.0
    var offsetY = 0.0

    node.onMousePressed = EventHandler {
        println("down '$node'")
        offsetX = node.translateX - it.sceneX
        offsetY = node.translateY - it.sceneY
        // we don't want to drag the background too
        it.consume()
    }

    node.onMouseDragged = EventHandler {
        print("drag")
        node.translateX = it.sceneX + offsetX
        node.translateY = it.sceneY + offsetY
        // we don't want to drag the background too
        it.consume()
    }
}




