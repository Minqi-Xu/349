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


    // to support hierarchical shapes
    var parent: Shape? = null

    protected val children = mutableListOf<Shape>()

    fun addChild(s: Shape) {
        s.parent = this
        children.add(s)
    }

    // need to call super.draw if shape can have children
    open fun draw(gc: GraphicsContext) {
        for (shape in children) {
            shape.draw(gc)
        }
    }

    abstract fun hittest(mx: Double, my: Double) : Boolean

    // region transformations

    // model-to-world transform including parent
    val worldTransform: Transform2D
        get() {
            val p = parent
            if (p == null) {
                // no parent, return only this model-to-world transform
                return transform
            } else {
                // there is a parent, return concatenation and
                // of parent and this model-to-world transforms
                return p.worldTransform.multiply(transform)
            }
        }

    // model-to-world transform applied when rendering
    var transform = Transform2D()

    // concatenate (i.e. post-multiply) another transformation
    fun concatTransform(t: Transform2D) {
        transform = transform.multiply(t)
    }

    // model transform applied before model-to-world transform

    var modelTransform = Transform2D()
     private set

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









