import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

// Draw using a canvas class instead.
class Main : Application() {
    override fun start(stage: Stage) {

        // Create a scene graph with a root node
        // This will hold the objects that we want to display on the stage
        val root = Group()

        // Create a border
        val border = BorderPane()

        // Create the model
        val model = Model()

        val initw: Double = 800.0
        val inith: Double = 700.0
        // Create a canvas as a drawing surface
        val canvas = CanvasView(model, initw, inith)
        canvas.


        model.addView(canvas)

        border.center = canvas

        // menubar
        val menubar = MenuBar()
        val fileMenu = Menu("File")
        val helpMenu = Menu("Help")
        val menuNew = MenuItem("New")
        val menuLoad = MenuItem("Load")
        val menuSave = MenuItem("Save")
        val menuQuit = MenuItem("Quit")
        val menuAbout = MenuItem("About")
        fileMenu.items.addAll(menuNew, menuLoad, menuSave, menuQuit)
        helpMenu.items.addAll(menuAbout)
        menubar.menus.addAll(fileMenu, helpMenu)

        // Create a VBox
        val vbox = VBox(menubar, border)

        val LeftToolBar = ToolbarView(model)
        model.addView(LeftToolBar)
        border.left = LeftToolBar



        // Add the canvas to the scene
        root.children.add(vbox)
        val scene = Scene(root, 900.0, 750.0, Color.WHITE)

        val sceneHandler = EventHandler<KeyEvent> {event ->
            if(event.getCode() == KeyCode.DELETE) {
                println("DELETE pressed")
                if(model.selecting != null) {
                    model.shapes.remove(model.selecting)
                    model.notifyObservers()
                    model.selecting = null
                }
            } else if(event.getCode() == KeyCode.ESCAPE) {
                println("ESC pressed")
                if(model.selecting != null) {
                    model.selecting = null
                }
            }
        }

        scene.addEventFilter(KeyEvent.KEY_PRESSED, sceneHandler)

        var dir: String = ""
        fun saving(add: String) {
            val newFile = File(add)
            newFile.printWriter().use { out->
                out.println(model.shapes.size)
                for(sh in model.shapes) {
                    out.println(sh.type)
                    out.println(sh.x1)
                    out.println(sh.y1)
                    out.println(sh.x2)
                    out.println(sh.y2)
                    out.println(sh.w)
                    out.println(sh.h)
                    out.println(sh.lineColor)
                    out.println(sh.fillColor)
                    out.println(sh.thick)
                    out.println(sh.style)
                }
            }
            println("End saving")
        }

        fun loading(add: String) {
            model.selectedTool = "Select"
            model.selectedLineColor = Color.BLACK
            model.selectedFillColor = Color.WHITE
            model.selectedThick = 1
            model.selectedStyle = 1
            model.shapes = ArrayList<MyShape>()
            model.selecting = null
            model.notifyObservers()
            val myFile = File(add)
            val scann = Scanner(myFile)
            val size = Integer.parseInt(scann.nextLine())
            for(i in 0..size-1) {
                val sh = MyShape()
                sh.type = scann.nextLine()
                sh.x1 = scann.nextLine().toDouble()
                sh.y1 = scann.nextLine().toDouble()
                sh.x2 = scann.nextLine().toDouble()
                sh.y2 = scann.nextLine().toDouble()
                sh.w = scann.nextLine().toDouble()
                sh.h = scann.nextLine().toDouble()
                sh.lineColor = Color.web(scann.nextLine())
                sh.fillColor = Color.web(scann.nextLine())
                sh.thick = Integer.parseInt(scann.nextLine())
                sh.style = Integer.parseInt(scann.nextLine())
                model.shapes.add(sh)
            }
            model.notifyObservers()
        }


        val dialog = TextInputDialog("")
        dialog.title = "Saving"
        dialog.headerText = "Please enter the full path save to"

        val confirmation1 = Alert(Alert.AlertType.CONFIRMATION)
        confirmation1.title = "Close and Save Confirmation Dialog"
        confirmation1.contentText = "Haven't saved, do you want to continue?"
        val confirmation2 = Alert(Alert.AlertType.CONFIRMATION)
        confirmation2.title = "Save Confirmation Dialog"
        confirmation2.contentText = "Do you want to save this drawing?"
        menuNew.setOnAction {
            val result1 = confirmation1.showAndWait()
            if(result1.get() == ButtonType.OK) {
                println("continue selected in dialog")
                model.selectedTool = "Select"
                model.selectedLineColor = Color.BLACK
                model.selectedFillColor = Color.WHITE
                model.selectedThick = 1
                model.selectedStyle = 1
                model.shapes = ArrayList<MyShape>()
                model.selecting = null
                model.notifyObservers()
            } else {
                println("CANCEL selected in dialog")
            }
        }
        menuSave.setOnAction {
            val result2 = confirmation2.showAndWait()
            if(result2.isPresent) {
                when(result2.get()) {
                    ButtonType.OK -> {
                        println("YES selected in dialog")
                        val add = dialog.showAndWait()
                        if(add.isPresent) {
                            dir = add.get();
                        }
                        saving(dir)
                    }
                    ButtonType.CANCEL -> {
                        println("CANCEL selected in dialog")
                    }
                }
            }
        }

        val dialog2 = TextInputDialog("")
        dialog2.title = "Loading"
        dialog2.headerText = "Please enter the full path load from"
        menuLoad.setOnAction {
            if(model.shapes.size != 0) {
                val result1 = confirmation1.showAndWait()
                if(result1.get() == ButtonType.OK) {
                    println("continue selected in dialog")
                    model.selectedTool = "Select"
                    model.selectedLineColor = Color.BLACK
                    model.selectedFillColor = Color.WHITE
                    model.selectedThick = 1
                    model.selectedStyle = 1
                    model.shapes = ArrayList<MyShape>()
                    model.selecting = null
                    model.notifyObservers()
                } else {
                    println("CANCEL selected in dialog")
                }
            }
            val add = dialog2.showAndWait()
            if(add.isPresent) {
                dir = add.get();
            }
            loading(dir)

        }

        menuQuit.setOnAction {
            if(model.shapes.size == 0) {
                Platform.exit()
            }
            val result1 = confirmation1.showAndWait()
            if(result1.isPresent) {
                when(result1.get()) {
                    ButtonType.OK -> {
                        println("Continue selected in dialog")
                        Platform.exit()
                    }
                    ButtonType.CANCEL -> {
                        println("CANCEL selected in dialog")
                    }
                }
            }
        }

        menuAbout.setOnAction {
            val confirmation = Alert(Alert.AlertType.INFORMATION)
            confirmation.title = "Informations"
            confirmation.contentText = "Sketching Tool\nMinqi Xu (m259xu)"
            val resultt = confirmation.showAndWait()
            resultt.get()
        }



        // Add the scene to the stage and show it
        stage.title = "Sketching Tool"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}
