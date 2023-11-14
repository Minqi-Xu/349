import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.transform.Affine
import javafx.scene.transform.NonInvertibleTransformException
import java.util.*

/**
 * A building block for creating your own shapes
 * These explicitly support parent-child relationships between nodes
 */
abstract class Sprite {
    var localID: String = ""
    private var parent: Sprite? = null
    private var localMatrix = Affine()
    protected var children = Vector<Sprite>()

    // maintain hierarchy
    fun addChild(s: Sprite) {
        children.add(s)
        s.setParent(this)
    }

    private fun setParent(s: Sprite) {
        parent = s
    }

    // transformations
    // these will pre-concat to the sprite's affine matrix
    fun translate(dx: Double, dy: Double) {
        localMatrix.prependTranslation(dx, dy)
    }

    @Throws(NonInvertibleTransformException::class)
    fun rotate(theta: Double) {
        val fullMatrix = fullMatrix
        val inverse = fullMatrix.createInverse()

        // move to the origin, rotate and move back
        localMatrix.prepend(inverse)
        localMatrix.prependRotation(theta)
        localMatrix.prepend(fullMatrix)
    }

    @Throws(NonInvertibleTransformException::class)
    fun scale(sx: Double, sy: Double) {
        val fullMatrix = fullMatrix
        val inverse = fullMatrix.createInverse()

        // move to the origin, rotate and move back
        localMatrix.prepend(inverse)
        localMatrix.prependScale(sx, sy)
        localMatrix.prepend(fullMatrix)
    }

    val fullMatrix: Affine
        get() {
            val fullMatrix = localMatrix.clone()
            fullMatrix.append( parent?.fullMatrix ?: Affine() )
            return fullMatrix
        }

    // hit tests
    // these cannot be handled in the base class, since the actual hit tests are dependent on the type of shape
    abstract operator fun contains(p: Point2D): Boolean

    fun contains(x: Double, y: Double): Boolean {
        return contains(Point2D(x, y))
    }

    // we can walk the tree from the base class, since we rely on the specific sprites to check containment
    fun getSpriteHit(x: Double, y: Double): Sprite? {
        // check me first...
        if (this.contains(x, y)) {
            return this
        }

        // if no match above, recurse through children and return the first hit
        // assumes no overlapping shapes
        for (sprite in children) {
            val hit = sprite.getSpriteHit(x, y)
            if (hit != null) return hit
        }
        return null
    }

    // drawing method
    abstract fun draw(gc: GraphicsContext)

    // debugging
    override fun toString(): String {
        return "Sprite $localID"
    }

    companion object {
        var spriteID = 0
    }

    init {
        spriteID +=1
        localID = spriteID.toString()
    }
}