import java.awt.Dimension
import javax.swing.JFrame

// CS 349 ShapeUndo Demo

class Main {
    var model: Model

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Main()
        }
    }

    init {
        val frame = JFrame("ShapeUndo")

        // create Model and initialize it
        // (value, min, max in this model)
        model = Model()

        // create View
        val view = CanvasView(model)

        // create Menu View
        val menuView = MenuView(model)

        // add views to the window
        frame.contentPane.add(view)
        frame.jMenuBar = menuView
        frame.preferredSize = Dimension(300, 300)
        frame.pack()
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isVisible = true

        // let all the views know that they're connected to the model
        model.updateViews()
    }
}
