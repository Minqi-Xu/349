import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.KeyStroke

// application top menu
// **NOTE**: Uses Java Swing UI Framework

class MenuView(
    private val model: Model
) : JMenuBar(), IView {

    // Undo menu items
    private val undoMenuItem: JMenuItem
    private val redoMenuItem: JMenuItem

    override fun update() {
        // update menu enabled state based on whether
        // undo or redo is possible
        undoMenuItem.isEnabled = model.canUndo
        redoMenuItem.isEnabled = model.canRedo
    }

    init {
        // create a menu UI with undo/redo
        val fileMenu = JMenu("File")
        val editMenu = JMenu("Edit")
        this.add(fileMenu)
        this.add(editMenu)

        // Create a "quit" menu item and add it to the file menu
        val quitMenuItem = JMenuItem("Quit")
        fileMenu.add(quitMenuItem)

        // quit menu controller
        quitMenuItem.addActionListener { System.exit(0) }

        // create undo and redo menu items
        undoMenuItem = JMenuItem("Undo")
        undoMenuItem.accelerator = KeyStroke.getKeyStroke(
            KeyEvent.VK_Z,
            ActionEvent.CTRL_MASK
        )
        redoMenuItem = JMenuItem("Redo")
        redoMenuItem.accelerator = KeyStroke.getKeyStroke(
            KeyEvent.VK_Y,
            ActionEvent.CTRL_MASK
        )
        editMenu.add(undoMenuItem)
        editMenu.add(redoMenuItem)

        // controllers for undo menu item
        undoMenuItem.addActionListener { model.undo() }
        // controller for redo menu item
        redoMenuItem.addActionListener { model.redo() }

        // set the model
        model.addObserver(this)
    }
}
