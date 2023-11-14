import javafx.geometry.Point2D
import javafx.scene.paint.Color
import javafx.scene.canvas.*

abstract class Shape(
    var fill: Color? = Color.LIGHTGREY,
    var stroke: Color? = Color.BLACK,
    var strokeWidth: Double = 1.0
) {

    open val isFilled: Boolean
        get() = (fill != null)

    open val isStroked: Boolean
        get() = (stroke != null)

    abstract fun draw(gc: GraphicsContext)

    abstract fun hittest(mx: Double, my: Double) : Boolean
}






