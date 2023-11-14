import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlin.system.exitProcess


class Main : Application() {
    override fun start(stage: Stage?) {
        // Create menu items
        val menubar = MenuBar()
        val fileMenu = Menu("File")
        val fileNew = MenuItem("New")
        val fileOpen = MenuItem("Open")
        val fileQuit = MenuItem("Quit")
        fileMenu.items.addAll(fileNew, fileOpen, fileQuit)

        val editMenu = Menu("Edit")
        val editCut = MenuItem("Cut")
        val editCopy = MenuItem("Copy")
        val editPaste = MenuItem("Paste")
        editMenu.items.addAll(editCut, editCopy, editPaste)

        val extraMenu = Menu("Extra")
        val extraToggle = RadioMenuItem("Toggle")
        extraMenu.items.add(extraToggle)

        // Map accelerator keys to menu items
        fileNew.accelerator = KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN)
        fileOpen.accelerator = KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN)
        fileQuit.accelerator = KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN)
        editCut.accelerator = KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN)
        editCopy.accelerator = KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN)
        editPaste.accelerator = KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN)
        extraToggle.accelerator = KeyCodeCombination(KeyCode.PERIOD, KeyCombination.META_DOWN)

        // Put menus together

        // Put menus together
        menubar.menus.addAll(fileMenu, editMenu, extraMenu)

        // Setup handlers
        fileNew.setOnAction { println("File-New") }
        fileOpen.setOnAction { println("File-Open") }
        fileQuit.setOnAction {
            println("File-Quit")
            exitProcess(0)
        }

        editCut.setOnAction { println("Edit-Cut") }
        editCopy.setOnAction { println("Edit-Copy") }
        editPaste.setOnAction { println("Edit-Paste") }
        extraToggle.setOnAction { println("Extra-Toggle: ${extraToggle.selectedProperty().get()}") }

        val instructions = Label("This app demonstrates how to setup menus. ")
        val root = VBox(menubar, instructions)
        // root.setPadding(new Insets(10));
        // root.setPadding(new Insets(10));
        root.spacing = 10.0

//        val os = System.getProperty("os.name")
//        if (os != null && os.startsWith("Mac")) {
//            menubar.useSystemMenuBarProperty().set(true)
//        }

        // Create a root node and add to the scene
        val scene = Scene(root, 400.0, 300.0)
        stage?.scene = scene
        stage?.isResizable = false
        stage?.title = "MenuBar Demo"
        stage?.show()
    }
}