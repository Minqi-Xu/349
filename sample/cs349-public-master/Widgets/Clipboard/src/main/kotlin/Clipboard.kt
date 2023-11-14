import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.input.*
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlin.system.exitProcess

class ClipboardDemo : Application() {
    override fun start(stage: Stage) {
        // Create menu items
        val menubar = MenuBar()
        val fileMenu = Menu("File")
        val quit = MenuItem("Quit")
        val editMenu = Menu("Edit")
        val cut = MenuItem("Cut")
        val copy = MenuItem("Copy")
        val paste = MenuItem("Paste")

        // Put menus together
        fileMenu.items.add(quit)
        editMenu.items.addAll(cut, copy, paste)
        menubar.menus.addAll(fileMenu, editMenu)

        // Create other on-screen widgets
        val instructions1 = Label(" Menu items use handlers that we've defined in code. ")
        val instructions2 = Label(" Right-click over the text field to see the built-in context menu.")

        val spacer1 = Label()
        val label1 = Label(" TextArea 1")
        val text1 = TextArea()
        text1.prefHeight = 150.0
        text1.isEditable = true
        text1.isWrapText = true

        val spacer2 = Label()
        val label2 = Label(" TextArea 2")
        val text2 = TextArea()
        text2.prefHeight = 150.0
        text2.isEditable = true
        text2.isWrapText = true

        // Map accelerator keys to menu items
        quit.accelerator = KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN)
        cut.accelerator = KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN)
        copy.accelerator = KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN)
        paste.accelerator = KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN)

        // Create a root node and add to the scene
        // This will stack widgets vertically
        val root = VBox(menubar, instructions1, instructions2, spacer1, label1, text1, spacer2, label2, text2)
        root.spacing = 5.0
        val scene = Scene(root, 400.0, 500.0)

        // Setup handlers
        // We needed the scene to be created previously since we refer to it here
        fileMenu.onAction = EventHandler {
            exitProcess(0)
        }
        cut.onAction = EventHandler {
            // pass in the selected text area widget
            // focus tells us which one to use as the source
            val source = scene.focusOwnerProperty().get() as TextArea
            putOnClipboard(source)
            source.clear()
        }
        copy.onAction = EventHandler {
            // pass in the selected text area widget
            // focus tells us which one to use as the source
            val source = scene.focusOwnerProperty().get() as TextArea
            putOnClipboard(source)
        }
        paste.onAction = EventHandler {
            // paste into the selected widget
            val destination = scene.focusOwnerProperty().get() as TextArea
            getFromClipboard(destination)
        }

        // Attach the scene to the stage and show it
        stage.scene = scene
        stage.isResizable = false
        stage.title = "Clipboard Demo"
        stage.show()
    }

    // take text contents of a TextArea widget and place on clipboard
    private fun putOnClipboard(text: TextArea) {
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(text.text)
        clipboard.setContent(content)
        println("Copy to clipboard: " + clipboard.string)
    }

    // return the string contents of the clipboard
    private fun getFromClipboard(text: TextArea) {
        val clipboard = Clipboard.getSystemClipboard()
        text.appendText(clipboard.string)
        println("Paste from clipboard: " + clipboard.string)
    }
}