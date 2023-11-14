import javafx.scene.canvas.GraphicsContext
import javafx.geometry.Point2D

class Line (
    var x1: Double,
    var y1: Double,
    var x2: Double,
    var y2: Double
): Shape() {

    override val isFilled = false

    override fun draw(gc: GraphicsContext) {
        if (isStroked) {
            gc.stroke = stroke
            gc.lineWidth = strokeWidth
            gc.strokeLine(x1, y1, x2, y2)
        }
    }

    override fun hittest(mx: Double, my: Double): Boolean {

        // edge hit-test
        if (isStroked) {
            val m = Point2D(mx, my)
            val q = closestPoint(m, Point2D(x1, y1), Point2D(x2, y2))
            if (m.distance(q) <= strokeWidth / 2) return true
        }

        // no hit
        return false
    }
}