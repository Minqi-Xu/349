import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import javafx.stage.Stage

class Main : Application() {
    private val SCREEN_WIDTH = 1000.0
    private val SCREEN_HEIGHT = 725.0

    private var startX = -1.0
    private var startY = -1.0

    private enum class STATE { NONE, DRAG }
    private var state = STATE.NONE

    override fun start(stage: Stage?) {
        // background
        val pane = Pane()
        pane.background = Background(BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY))
        pane.prefHeight = SCREEN_HEIGHT
        pane.prefWidth = SCREEN_WIDTH

        // movable box, rotated 45 degrees
        val shape = Rectangle(100.0, 100.0, Paint.valueOf("DARKGRAY"))
        shape.x = 150.0
        shape.y = 150.0

        shape.setOnMousePressed { event ->
            startX = event.x
            startY = event.y
            state = STATE.DRAG
        }

        shape.setOnMouseDragged { event ->
            if (state == STATE.DRAG) {
                val dx = event.x - startX
                val dy = event.y - startY

                shape.x += dx
                shape.y += dy

                startX = event.x
                startY = event.y
            }
        }

        shape.setOnMouseReleased {
            state = STATE.NONE
        }

//        shape.setOnMouseExited{
//            state = STATE.NONE
//        }

        // setup scene and stage
        pane.children.add(shape)
        stage?.scene = Scene(pane)
        stage?.isResizable = false
        stage?.height = SCREEN_HEIGHT + 27  // account for titlebar
        stage?.width = SCREEN_WIDTH
        stage?.title = "Drag Shape Demo"
        stage?.show()
    }
}