import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import kotlin.math.cos
import kotlin.math.sin

class ClockFace(var cx: Double, var cy: Double, var radius: Double) {
    // inner class
    internal inner class Point(var x: Double, var y: Double)

    // constants
    val BACKGROUND_COLOR = Color.LIGHTGRAY
    val HOURS_COLOR = Color.DARKBLUE
    val MINUTES_COLOR = Color.BLUE
    val SECONDS_COLOR = Color.ALICEBLUE
    var MINUTES_LENGTH = 0.0
    var HOURS_LENGTH = 0.0
    var SECONDS_LENGTH = 0.0
    val HANDS_WIDTH = 3f
    var frame = Circle()
    var minutes = Line()
    var hours = Line()
    var seconds = Line()
    fun build(): Node {
        return Group(frame, hours, minutes, seconds)
    }

    fun setTime(h: Int, m: Int, s: Int) {
        var angle = findAngleInDegrees(h, 12)
        var end = findPointOnCircle(cx, cy, HOURS_LENGTH, angle)
        hours.endX = end.x
        hours.endY = end.y

        angle = findAngleInDegrees(m, 60)
        end = findPointOnCircle(cx, cy, MINUTES_LENGTH, angle)
        minutes.endX = end.x
        minutes.endY = end.y

        angle = findAngleInDegrees(s, 60)
        end = findPointOnCircle(cx, cy, SECONDS_LENGTH, angle)
        seconds.endX = end.x
        seconds.endY = end.y
    }

    private fun findPointOnCircle(x: Double, y: Double, l: Double, angle: Double): Point {
        val px = l * cos(Math.toRadians(angle)) + x
        val py = l * sin(Math.toRadians(angle)) + y
        return Point(px, py)
    }

    private fun findAngleInDegrees(value: Int, range: Int): Double {
        return (value * 360 / range - 90).toDouble() // e.g. 3:00 would return 3*360/12 or 90 degrees
    }

    // constructor
    init {
        // setup background frame
        frame.centerX = cx
        frame.centerY = cy
        frame.radius = radius
        frame.fill = BACKGROUND_COLOR

        // setup hands
        hours.stroke = HOURS_COLOR
        hours.strokeWidth = HANDS_WIDTH.toDouble()
        hours.startX = cx
        hours.startY = cy
        HOURS_LENGTH = radius - 20

        minutes.stroke = MINUTES_COLOR
        minutes.strokeWidth = HANDS_WIDTH.toDouble()
        minutes.startX = cx
        minutes.startY = cy
        MINUTES_LENGTH = radius - 15

        seconds.stroke = SECONDS_COLOR
        seconds.strokeWidth = HANDS_WIDTH.toDouble()
        seconds.startX = cx
        seconds.startY = cy
        SECONDS_LENGTH = radius - 5
    }
}