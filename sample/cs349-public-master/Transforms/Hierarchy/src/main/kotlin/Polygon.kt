import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class Polygon(
    var points: List<Point2D>
): Shape() {

    val numPoints: Int
        get() = points.size

    override fun draw(gc: GraphicsContext) {

        // apply all the transforms to these points
        val tp = points.map { p -> worldTransform.multiply(
                                   modelTransform.multiply(p)) }

        // convert to separate arrays for x and y
        val xPoints = tp.map { p -> p.x }.toDoubleArray()
        val yPoints = tp.map { p -> p.y }.toDoubleArray()

        // draw geometry in gc
        if (isFilled) {
            gc.fill = fill
            gc.fillPolygon(xPoints, yPoints, numPoints)
        }
        if (isStroked) {
            gc.stroke = stroke
            gc.lineWidth = strokeWidth
            gc.strokePolygon(xPoints, yPoints, numPoints)
        }
        // now draw all the children too
        super.draw(gc)
    }

    override fun hittest(mx: Double, my: Double): Boolean {

        // inside hit-test
        if (isFilled) {
        }

        // edge hit-test
        if (isStroked) {
            val m = Point2D(mx, my)

//            // partial implementation which isn't fully working with scale
//            // transform mouse into geometry coordinates
//            val transformInverse = worldTransform.multiply(translate(100.0, 100.0).multiply(modelTransform)).inverse()
//            val mT = transformInverse.multiply(m)
//            val t = transformInverse.multiplyAsVector(Point2D(strokeWidth, strokeWidth))
//            val strokeWidthT = t.x
//            println("$strokeWidth, $strokeWidthT, $t")

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

    // region extra debug and viz

    // draw two versions of the shape (stroke only):
    // untransformed model geometry in GRAY
    // geometry only transformed my model transform in BLACK
    fun debugDraw(gc: GraphicsContext) {

        run {
            // convert to seperate arrays for x and y
            val xPoints = points.map { p -> p.x }.toDoubleArray()
            val yPoints = points.map { p -> p.y }.toDoubleArray()

            gc.stroke = Color.DARKGREY
            gc.lineWidth = 4.0
            gc.strokePolygon(xPoints, yPoints, numPoints)
        }

        run {
            val tp = points.map { p -> modelTransform.multiply(p) }
            val xPoints = tp.map { p -> p.x }.toDoubleArray()
            val yPoints = tp.map { p -> p.y }.toDoubleArray()

            gc.stroke = Color.BLACK
            gc.lineWidth = 1.5
            gc.strokePolygon(xPoints, yPoints, numPoints)
        }
    }

    // endregion
}