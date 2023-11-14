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

    // region model-to-world transformations

    // model-to-world transform applied when rendering
    var transform = Transform2D()

    // concatenate (i.e. post-multiply) another transformation
    fun concatTransform(t: Transform2D) {
        transform = transform.multiply(t)
    }

    // endregion

    // region model transform

    // model transform applied before model-to-world transform
    protected var modelTransform = Transform2D()

    private fun updateModelTransform() {
        modelTransform =
            scale(scaleX, scaleY).multiply(
                rotate(rotate).multiply(
                    translate(translateX, translateY)))
    }

    var translateX: Double = 0.0
        set(v) { field = v; updateModelTransform() }

    var translateY: Double = 0.0
        set(v) { field = v; updateModelTransform() }

    var rotate: Double = 0.0
        set(v) { field = v; updateModelTransform() }

    var scaleX: Double = 1.0
        set(v) { field = v; updateModelTransform() }

    var scaleY: Double = 1.0
        set(v) { field = v; updateModelTransform() }

    // endregion

}









