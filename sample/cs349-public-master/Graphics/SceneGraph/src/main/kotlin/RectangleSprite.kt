import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.transform.NonInvertibleTransformException

class RectangleSprite(private val w: Double, private val h: Double) : Sprite() {
    private var x: Double= 0.0
    private var y: Double = 0.0

    // Draw on the supplied canvas
    override fun draw(gc: GraphicsContext) {
        // save the current graphics context so that we can restore later
        val oldMatrix = gc.transform

        // make sure we have the correct transformations for this shape
        gc.transform = fullMatrix
        gc.stroke = Color.BLUE
        gc.strokeRect(x, y, w, h)
        gc.strokeText(localID, x + w / 2 - 3, y + h / 2 + 3)

        // draw children
        for (child in children) {
            child.draw(gc)
        }

        // set back to original value since we're done with this branch of the scene graph
        gc.transform = oldMatrix
    }

    // Check if the point is contained by this shape
    // This cannot be abstract, since it relies on knowledge of the
    // specific type of shape for the hit test.
    override operator fun contains(p: Point2D): Boolean {
        try {
            // Use inverted matrix to move the mouse click so that it's
            // relative to the shape model at the origin.
            val pointAtOrigin: Point2D = fullMatrix.createInverse().transform(p)

            // Perform the hit test relative to the shape model's
            // untranslated coordinates at the origin
            return Rectangle(x, y, w, h).contains(pointAtOrigin)
        } catch (e: NonInvertibleTransformException) {
            e.printStackTrace()
        }
        return false
    }
}