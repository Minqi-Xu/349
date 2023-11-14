import javafx.scene.canvas.GraphicsContext

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
fun GraphicsContext.bar(x1: Double, y1: Double, x2: Double, y2:Double) {
    val gc = this
    val d = 5.0
    gc.strokeLine(x1, y1, x2, y2)
    gc.fillOval(x1 - d/2, y1 - d/2, d, d)
    gc.fillOval(x2 - d/2, y2 - d/2, d, d)
}

