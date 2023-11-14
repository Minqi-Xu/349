import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext

class Polygon(
    var points: List<Point2D>
): Shape() {

    val xPoints: DoubleArray
        get() = points.map { x -> x.x }.toDoubleArray()

    val yPoints: DoubleArray
        get() = points.map { x -> x.y }.toDoubleArray()

    val numPoints: Int
        get() = points.size

    override fun draw(gc: GraphicsContext) {
        if (isFilled) {
            gc.fill = fill
            gc.fillPolygon(xPoints, yPoints, numPoints)
        }
        if (isStroked) {
            gc.stroke = stroke
            gc.lineWidth = strokeWidth
            gc.strokePolygon(xPoints, yPoints, numPoints)
        }
    }

    override fun hittest(mx: Double, my: Double): Boolean {

        // inside hit-test
        if (isFilled) {
        }

        // edge hit-test
        if (isStroked) {
            val m = Point2D(mx, my)
            // assume shape is closed, so start with segment
            // from last point to first point
            var p0 = points.last()
            for (p1 in points) {
                val q = closestPoint(m, p0, p1)
                if (m.distance(q) < strokeWidth / 2) return true
                p0 = p1
            }
        }

        // no hit
        return false
    }
}