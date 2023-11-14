import java.awt.*
import java.awt.event.MouseEvent
import java.lang.reflect.InvocationTargetException
import javax.swing.JFrame
import javax.swing.JPanel

/*
* CS 349 Java Code Examples
* Binding events by tapping into the low-level java event queue.
* Probably not the best way to do this! Just a demo.
*
*/
class Main internal constructor() : JPanel() {
    var px = 0
    var py = 0
    private var size = 50

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        g2.color = Color.RED
        g2.fillOval(px - size / 2, py - size / 2, size, size)
    }

    // kind of like an event loop
    private inner class MyEventQueue : EventQueue() {
        // mouse events come in here
        public override fun dispatchEvent(event: AWTEvent) {
            //System.out.println("dispatchEvent " + e.getID() );
            if (event.id == MouseEvent.MOUSE_DRAGGED) {
                val me = event as MouseEvent
                px = me.x
                py = me.y
                println("($x,$y)")
                repaint()
            }
            super.dispatchEvent(event)
        }
    }

    companion object {
        @Throws(InterruptedException::class, InvocationTargetException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val panel = Main()
            val f = JFrame("EventQueue")
            f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            f.setSize(200, 200)
            f.contentPane = panel
            f.isVisible = true
        }
    }

    init {
        EventQueue.invokeAndWait {
            val eq = Toolkit.getDefaultToolkit().systemEventQueue
            // replace the current event queue with MyEventQueue
            eq.push(MyEventQueue())
            println("Run")
        }
    }
}