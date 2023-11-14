import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.JFrame
import javax.swing.JPanel

/*
* CS 349 Code Examples
* AdapterEvents
* Binding events by creating a child listener adapter object.
*/
class Adapter internal constructor() : JPanel() {
    init {
        addMouseMotionListener(MyMouseMotionListener())
    }

    internal inner class MyMouseMotionListener : MouseMotionAdapter() {
        override fun mouseDragged(e: MouseEvent) {
            px = e.x
            py = e.y
            repaint()
        }
    }

    private var px = 0
    private var py = 0

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        g2.color = Color.RED
        g2.fillOval(px - 50 / 2, py - 50 / 2, 50, 50)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val panel = Adapter()
            val f = JFrame("Adapter")
            f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            f.setSize(200, 200)
            f.contentPane = panel
            f.isVisible = true
        }
    }
}