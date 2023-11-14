import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel

// main canvas area where the shape position is dragged
// **NOTE**: Uses Java Swing UI Framework

internal class CanvasView(
    val model: Model
) : JPanel(), IView {

    var lastX = 0
    var lastY = 0

    private var needStore = false

    // Observer interface
    override fun update() {
        // this will call paintComponent when the UI thread is ready
        repaint()
    }

    // custom graphics drawing
    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g) // JPanel paint
        val g2 = g as Graphics2D
        g2.paint = Color.red
        g2.fill(model.shape)
    }

    init {
        // create the view UI
        this.layout = FlowLayout()

        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                lastX = e.x
                lastY = e.y
            }
            override fun mouseReleased(e: MouseEvent) {
                if (needStore) {
                    model.endEdit()
                    needStore = false
                }
            }
        })

        addMouseMotionListener(object : MouseAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                model.translate(e.x - lastX, e.y - lastY)
                lastX = e.x
                lastY = e.y
                needStore = true
            }
        })

        // set the model
        model.addObserver(this)
    }
}
