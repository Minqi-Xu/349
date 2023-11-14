import java.awt.FlowLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

// application main control panel with slides and buttons
// **NOTE**: Uses Java Swing UI Framework

internal class ControlView(
    val model: Model
    ) : JPanel(), IView {

    private val slider: JSlider
    private val text: JTextField
    private val increment: JButton
    private val decrement: JButton

    override fun update() {
        // update component values based on model
        println("View: update")
        slider.value = model.getValue()
        slider.minimum = model.min
        slider.maximum = model.max
        text.text = Integer.toString(model.getValue())
    }

    init {
        // create the view UI
        this.layout = FlowLayout()

        // slider to choose exact value
        slider = JSlider()
        this.add(slider)

        // text boc to change value
        text = JTextField("X", 4)
        this.add(text)

        // increment/decrement buttons
        decrement = JButton("-")
        increment = JButton("+")
        this.add(decrement)
        this.add(increment)
        this.add(JLabel("shortcut keys: CTRL-Z to undo, CTRL-Y to redo"))

        // change the flag and see what happens
        val noChunking = false
        if (noChunking) {
            slider.addChangeListener { model.setValue(slider.value) }
        } else {

            // controller for when a drag event finishes
            // this is the right undo "chunk"
            slider.addMouseListener(object : MouseAdapter() {
                override fun mouseReleased(e: MouseEvent) {
                    model.setValue(slider.value)
                }
            })
        }

        // add a controller for text edits too
        // (will only fire when enter is pressed after editing ...)
        text.addActionListener {
            try {
                model.setValue(text.text.toInt())
            } catch (ex: NumberFormatException) {
                // not a number, just update views to reset it
                // (need to be careful here not to insert an undo)
                model.updateViews()
            }
        }
        increment.addActionListener { model.incrementValue() }
        decrement.addActionListener { model.decrementValue() }

        // set the model
        model.addObserver(this)
    }
}
