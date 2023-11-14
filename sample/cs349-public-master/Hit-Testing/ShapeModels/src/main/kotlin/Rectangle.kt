import javafx.scene.canvas.GraphicsContext

class Rectangle(
    var x: Double,
    var y: Double,
    var w: Double,
    var h: Double,
): Shape() {

    override fun draw(gc: GraphicsContext) {
        if (isFilled) {
            gc.fill = fill
            gc.fillRect(x, y, w, h)
        }
        if (isStroked) {
            gc.stroke = stroke
            gc.lineWidth = strokeWidth
            gc.strokeRect(x, y, w, h)
        }
    }

    override fun hittest(mx: Double, my: Double): Boolean {

        // inside hit-test
        if (isFilled) {
            if (mx >= x && mx <= x + w &&
                my >= y && my <= y + h) return true
        }

        // edge hit-test
        // width of stroke on either side of edges
        if (isStroked) {
            val s = strokeWidth / 2
            if (// must be inside outer rect after adding stroke
                (mx >= x - s && mx <= x + w + s &&
                        my >= y - s && my <= y + h + s)
                // but NOT inside inner rect after subtracting stroke
                && !
                (mx >= x + s && mx <= x + w - s &&
                        my >= y + s && my <= y + h - s)
            ) return true
        }

        // no hit
        return false
    }
}
