import java.awt.Dimension
import javax.swing.JFrame

// Value Undo Demo
// **NOTE**: Uses Java Swing UI Framework

class Main {
    var model: Model

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Main()
        }
    }

    init {
        val frame = JFrame("ValueUndo")

        // create Model and initialize it
        // (value, min, max in this model)
        model = Model(22, 0, 100)

        // create View
        val view = ControlView(model)

        // create Menu View
        val menuView = MenuView(model)

        // add views to the window
        frame.contentPane.add(view)
        frame.jMenuBar = menuView
        frame.preferredSize = Dimension(500, 120)
        frame.pack()
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isVisible = true

        // let all the views know that they're connected to the model
        model.updateViews()
    }
}
