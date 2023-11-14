import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage) {
        val pane = Pane()
        val scene = Scene(pane)

        val line1 = Line(20.0, 40.0, 270.0, 40.0)
        line1.strokeDashArray.addAll(25.0, 20.0, 5.0, 20.0)
        line1.strokeWidth = 5.0

        val line2 = Line(20.0, 60.0, 270.0, 60.0)
        line2.strokeDashArray.addAll(25.0, 20.0, 5.0, 20.0)
        line2.strokeWidth = 8.0

        val line3 = Line(20.0, 80.0, 270.0, 80.0)
        line3.strokeDashArray.addAll(25.0, 20.0, 5.0, 20.0)
        line3.strokeWidth = 10.0

        val rect1 = Rectangle(50.0, 130.0, 50.0, 50.0)
        rect1.strokeDashArray.addAll(25.0, 20.0, 5.0, 20.0)
        rect1.fill = Color.WHITE
        rect1.stroke = Color.BLACK
        rect1.strokeWidth = 5.0

        val circle1 = Circle(200.0, 200.0, 50.0)
        circle1.strokeDashArray.addAll(25.0, 20.0, 5.0, 20.0)
        circle1.fill = Color.WHITE
        circle1.stroke = Color.BLACK
        circle1.strokeWidth = 5.0

        pane.children.addAll(line1, line2, line3, rect1, circle1)
        stage.isResizable = false
        stage.scene = scene
        stage.height = 300.0
        stage.show()
    }
}