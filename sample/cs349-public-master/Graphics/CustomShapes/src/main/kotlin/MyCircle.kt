import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color

internal class MyCircle(
    private val x: Int, private val y: Int, private val r: Int, private val color: Color) : IShape {

    override fun draw(canvas: Canvas?) {
        val gc = canvas!!.graphicsContext2D
        gc.fill = color
        gc.fillOval(x.toDouble(), y.toDouble(), (r * 2).toDouble(), (r * 2).toDouble())
    }
}