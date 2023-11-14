import javafx.scene.paint.Color
import javafx.scene.shape.Shape

enum class WidgetState { DEFAULT, OVER, DOWN, DISABLED }

// simple class to manage state
class StateFeedback(val shape: Shape) {

    var state = WidgetState.DEFAULT

    init { update(state) }

    fun update(newState: WidgetState) {
        // transition into new state
        when (newState) {
            WidgetState.DEFAULT -> with(shape) {
                fill = Color.LIGHTSLATEGRAY
                stroke = Color.BLACK
                scaleX = 1.0
                scaleY = 1.0
            }
            WidgetState.OVER -> with(shape) {
                fill = Color.LIGHTSLATEGRAY.brighter()
                stroke = Color.GOLDENROD
                scaleX = 1.0
                scaleY = 1.0
            }
            WidgetState.DOWN -> with(shape) {
                fill = Color.LIGHTSLATEGRAY.brighter()
                stroke = Color.GOLDENROD
                scaleX = 0.96
                scaleY = 0.96
            }
            WidgetState.DISABLED -> with(shape) {
                fill = Color.LIGHTGRAY
                stroke = Color.GRAY.brighter()
                scaleX = 1.0
                scaleY = 1.0
            }
        }
        state = newState
    }
}