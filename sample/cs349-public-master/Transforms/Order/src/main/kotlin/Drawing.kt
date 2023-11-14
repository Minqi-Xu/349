import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

// grid GraphicsContext extension
fun GraphicsContext.grid(x: Double, y: Double, wh: Double, s: Double) {
    val gc = this
    var i = 0.0
    while (i <= wh) {
        gc.strokeLine(x + i, y, x + i, y + wh)
        gc.strokeLine(x, y + i, x + wh, y + i )
        i += s
    }
}

// bar GraphicsContext extension
// first point is open, second closed
fun GraphicsContext.bar(x1: Double, y1: Double, x2: Double, y2:Double, c: Color) {
    val gc = this

    gc.lineWidth = 1.0
    gc.fill = c
    gc.stroke = c

    val d = 5.0
    gc.strokeLine(x1, y1, x2, y2)
    gc.strokeOval(x1 - d/2, y1 - d/2, d, d)
    gc.fillOval(x2 - d/2, y2 - d/2, d, d)
}

// house GraphicsContext extension