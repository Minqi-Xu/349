import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color

internal class MyRectangle(
    private val x: Int, private val y: Int, private val w: Int, private val h: Int, private val color: Color) : IShape {

    override fun draw(canvas: Canvas?) {
        val gc = canvas!!.graphicsContext2D
        gc.fill = color
        gc.fillRect(x.toDouble(), y.toDouble(), w.toDouble(), h.toDouble())
    }
}