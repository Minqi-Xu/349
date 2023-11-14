import javafx.application.Application
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.File
import java.lang.Double.min
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

class Main : Application() {
    var hiddenflag = false // false means do not show hidden files, true means show hidden files
    override fun start(stage: Stage) {
        // return a mutablelist that do not contain the hidden files
        fun changelst(path: String, templst: MutableList<String>): MutableList<String> {
            var dir = File(path)
            if(dir.list() != null) {
                for(entries in dir.list()) {
                    var fileName = entries
                    if(fileName[0] != '.') {
                        templst.add(fileName)
                    }
                }
            }
            return templst
        }

        // return the full mutablelist which contains the hidden files
        fun getFullLst(path: String, templst: MutableList<String>): MutableList<String> {
            var dir = File(path)
            if(dir.list() != null) {
                for(entries in dir.list()) {
                    var fileName = entries
                    templst.add(fileName)
                }
            }
            return templst
        }





        // CREATE WIDGETS TO DISPLAY
        // menubar & toolbar
        val menuBar = MenuBar()
        val fileMenu = Menu("File")
        val actionMenu = Menu("Actions")
        val optionMenu = Menu("Options")


        val menuQuit = MenuItem("Quit")
        val menuDelete = MenuItem("Delete")
        val menuRename = MenuItem("Rename")
        val menuMove = MenuItem("Move")
        val menuShowHiddenFiles = MenuItem("Show Hidden Files")


        menuQuit.setOnAction { Platform.exit() }

        fileMenu.items.addAll(menuQuit)
        actionMenu.items.addAll(menuRename, menuMove, menuDelete)
        optionMenu.items.addAll(menuShowHiddenFiles)
        menuBar.menus.addAll(fileMenu, actionMenu, optionMenu)

        // determine starting directory
        // this will be the "test" subfolder in your project directory
        val homePath = "${System.getProperty("user.dir")}/test/"
        var curPath = "${System.getProperty("user.dir")}/test/"
        var selectedDir = ""

        val toolbar = ToolBar()
        val button1 = Button("Home")

        val button2 = Button("Prev")

        val button3 = Button("Next")

        val button4 = Button("Delete")

        val button5 = Button("Rename")

        val button6 = Button("Move")

        toolbar.items.addAll(button1, button2, button3, button4, button5, button6)
        // stack menu and toolbar in the top region
        val vbox = VBox(menuBar, toolbar)


        var flag_success = true



        // panel on left side, need to replace with a tree
        val list = ListView<String>()
        list.prefWidth = 225.0
        list.prefHeight = 450.0
        var templst = mutableListOf("")
        templst.removeLast()
        var tempFileList: MutableList<String>
        if(hiddenflag == false) {
            tempFileList = changelst(homePath, templst)
        } else {
            tempFileList = getFullLst(homePath, templst)
        }

        list.items = FXCollections.observableArrayList(tempFileList)
        //list.selectionModel.selectIndices(0)



        var Statuslabel = Label()
        var status = Pane(Statuslabel)
        status.prefHeight = 18.0
        status.prefWidth = 800.0
        status.style = "-fx-background-colour: gray"



        // SETUP LAYOUT
        val border = BorderPane()
        border.top = vbox
        border.left = list
        border.bottom = status

        list.selectionModel.selectedItemProperty().addListener { _, oldValue, newValue ->
            println("$oldValue --> $newValue")
            val str = "$newValue"
            selectedDir = curPath + str
            println("New selected: $selectedDir")
            val len = str.length
            val FILE = File(selectedDir)
            if((str[len-4] == '.' && str[len-3] == 't' && str[len-2] == 'x' && str[len-1] == 't')
                || (str[len-3] == '.' && str[len-2] == 'm' && str[len-1] == 'd')) {
                // if the selected one is text file
                try {
                    val f = Paths.get(selectedDir)
                    val content = Files.readString(f)
                    val txt = Label(content)
                    val centerPane = ScrollPane(txt)
                    border.center = centerPane
                } catch(e: Exception) {
                    val templable = Label("Cannot read File")
                    border.center = templable
                }
                Statuslabel = Label(selectedDir)
                status = Pane(Statuslabel)
                status.prefHeight = 18.0
                status.prefWidth = 800.0
                status.style = "-fx-background-colour: gray"
                border.bottom = status

            } else if((str[len-4] == '.' && str[len-3] == 'p' && str[len-2] == 'n' && str[len-1] == 'g')
                    || (str[len-4] == '.' && str[len-3] == 'j' && str[len-2] == 'p' && str[len-1] == 'g')
                    || (str[len-4] == '.' && str[len-3] == 'b' && str[len-2] == 'm' && str[len-1] == 'p')) {
                // if the selected one is an image
                try {
                    val image = ImageView(Image(selectedDir))
                    val factor = min((575/(Image(selectedDir).width)),(450/(Image(selectedDir).height)))
                    image.setFitWidth(Image(selectedDir).width * factor)
                    image.setFitHeight(Image(selectedDir).height * factor)
                    border.center = image
                } catch(e:Exception) {
                    val templable = Label("Cannot read File")
                    border.center = templable
                }
                Statuslabel = Label(selectedDir)
                status = Pane(Statuslabel)
                status.prefHeight = 18.0
                status.prefWidth = 800.0
                status.style = "-fx-background-colour: gray"
                border.bottom = status
            } else if(FILE.isDirectory()){
                val blankPane = Pane()
                blankPane.style = "-fx-background-colour: white"
                border.center = blankPane
                Statuslabel = Label(selectedDir)
                status = Pane(Statuslabel)
                status.prefHeight = 18.0
                status.prefWidth = 800.0
                status.style = "-fx-background-colour: gray"
                border.bottom = status
            } else {
                val tempBorder = BorderPane()
                val messages = Label("Unsupported type")
                tempBorder.center = messages
                border.center = tempBorder
                Statuslabel = Label(selectedDir)
                status = Pane(Statuslabel)
                status.prefHeight = 18.0
                status.prefWidth = 800.0
                status.style = "-fx-background-colour: gray"
                border.bottom = status
            }

        }



        // CREATE AND SHOW SCENE
        var scene = Scene(border, 800.0, 600.0)
        stage.scene = scene
        stage.title = "File Browser"
        stage.show()

        // change directory
        fun changeDir() {
            val FILE = File(selectedDir)
            if(FILE.isDirectory()) {
                curPath = selectedDir
                if(curPath[curPath.length-1] != '/' && curPath[curPath.length-1] != '\\') {
                    curPath = curPath + "/"
                }

                val list = ListView<String>()
                list.prefWidth = 225.0
                list.prefHeight = 450.0
                var templst = mutableListOf("")
                templst.removeLast()
                var tempFileList: MutableList<String>
                if(hiddenflag == false) {
                    tempFileList = changelst(curPath, templst)
                } else {
                    tempFileList = getFullLst(curPath, templst)
                }
                selectedDir = ""

                list.items = FXCollections.observableArrayList(tempFileList)
                //list.selectionModel.selectIndices(0)
                list.selectionModel.selectedItemProperty().addListener { _, oldValue, newValue ->
                    println("$oldValue --> $newValue")
                    val str = "$newValue"
                    selectedDir = curPath + str
                    println("New selected: $selectedDir")
                    val len = str.length
                    val FILE = File(selectedDir)
                    if((str[len-4] == '.' && str[len-3] == 't' && str[len-2] == 'x' && str[len-1] == 't')
                        || (str[len-3] == '.' && str[len-2] == 'm' && str[len-1] == 'd')) {
                        // if the selected one is text file
                        try {
                            val f = Paths.get(selectedDir)
                            val content = Files.readString(f)
                            val txt = Label(content)
                            val centerPane = ScrollPane(txt)
                            border.center = centerPane
                        } catch(e: Exception) {
                            val templabel = Label("Cannot read File")
                            border.center = templabel
                        }
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else if((str[len-4] == '.' && str[len-3] == 'p' && str[len-2] == 'n' && str[len-1] == 'g')
                        || (str[len-4] == '.' && str[len-3] == 'j' && str[len-2] == 'p' && str[len-1] == 'g')
                        || (str[len-4] == '.' && str[len-3] == 'b' && str[len-2] == 'm' && str[len-1] == 'p')) {
                        // if the selected one is an image
                        try {
                            val image = ImageView(Image(selectedDir))
                            val factor = min((575/(Image(selectedDir).width)),(450/(Image(selectedDir).height)))
                            image.setFitWidth(Image(selectedDir).width * factor)
                            image.setFitHeight(Image(selectedDir).height * factor)
                            border.center = image
                        } catch(e: Exception) {
                            val templabel = Label("Cannot read File")
                            border.center = templabel
                        }
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else if(FILE.isDirectory()){
                        val blankPane = Pane()
                        blankPane.style = "-fx-background-colour: white"
                        border.center = blankPane
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else {
                        val tempBorder = BorderPane()
                        val messages = Label("Unsupported type")
                        tempBorder.center = messages
                        border.center = tempBorder
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    }

                }
                border.left = list
                Statuslabel = Label()
                status = Pane(Statuslabel)
                status.prefHeight = 18.0
                status.prefWidth = 800.0
                status.style = "-fx-background-colour: gray"
                border.bottom = status
                val blankPane = Pane()
                blankPane.style = "-fx-background-colour: white"
                border.center = blankPane
            }
        }

        // change to previous directory
        fun backDir() {
            var countlen = 0
            val curLen = curPath.length
            for(i in 0..curLen-1) {
                if((curPath[i] == '\\' || curPath[i] == '/') && i != curLen-1) {
                    countlen = i
                }
            }
            var tempstring = ""
            for(i in 0..countlen-1) {
                tempstring = tempstring + curPath[i]
            }

            val FILE = File(tempstring)
            if(FILE.isDirectory()) {
                curPath = tempstring
                if(curPath[curPath.length-1] != '/' && curPath[curPath.length-1] != '\\') {
                    curPath = curPath + "/"
                }

                val list = ListView<String>()
                list.prefWidth = 225.0
                list.prefHeight = 450.0
                var templst = mutableListOf("")
                templst.removeLast()
                var tempFileList: MutableList<String>
                if(hiddenflag == false) {
                    tempFileList = changelst(curPath, templst)
                } else {
                    tempFileList = getFullLst(curPath, templst)
                }
                selectedDir = ""

                list.items = FXCollections.observableArrayList(tempFileList)
                //list.selectionModel.selectIndices(0)
                list.selectionModel.selectedItemProperty().addListener { _, oldValue, newValue ->
                    println("$oldValue --> $newValue")
                    val str = "$newValue"
                    selectedDir = curPath + str
                    println("New selected: $selectedDir")
                    val len = str.length
                    val FILE = File(selectedDir)
                    if((str[len-4] == '.' && str[len-3] == 't' && str[len-2] == 'x' && str[len-1] == 't')
                        || (str[len-3] == '.' && str[len-2] == 'm' && str[len-1] == 'd')) {
                        // if the selected one is text file
                        try {
                            val f = Paths.get(selectedDir)
                            val content = Files.readString(f)
                            val txt = Label(content)
                            val centerPane = ScrollPane(txt)
                            border.center = centerPane
                        } catch(e: Exception) {
                            val templabel = Label("Cannot read File")
                            border.center = templabel
                        }
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else if((str[len-4] == '.' && str[len-3] == 'p' && str[len-2] == 'n' && str[len-1] == 'g')
                        || (str[len-4] == '.' && str[len-3] == 'j' && str[len-2] == 'p' && str[len-1] == 'g')
                        || (str[len-4] == '.' && str[len-3] == 'b' && str[len-2] == 'm' && str[len-1] == 'p')) {
                        // if the selected one is an image
                        try {
                            val image = ImageView(Image(selectedDir))
                            val factor = min((575/(Image(selectedDir).width)),(450/(Image(selectedDir).height)))
                            image.setFitWidth(Image(selectedDir).width * factor)
                            image.setFitHeight(Image(selectedDir).height * factor)
                            border.center = image
                        } catch(e: Exception) {
                            val templabel = Label("Cannot read File")
                            border.center = templabel
                        }
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else if(FILE.isDirectory()){
                        val blankPane = Pane()
                        blankPane.style = "-fx-background-colour: white"
                        border.center = blankPane
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else {
                        val tempBorder = BorderPane()
                        val messages = Label("Unsupported type")
                        tempBorder.center = messages
                        border.center = tempBorder
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    }

                }
                border.left = list
                Statuslabel = Label()
                status = Pane(Statuslabel)
                status.prefHeight = 18.0
                status.prefWidth = 800.0
                status.style = "-fx-background-colour: gray"
                border.bottom = status
                val blankPane = Pane()
                blankPane.style = "-fx-background-colour: white"
                border.center = blankPane
            }
        }

        button3.setOnAction {
            println("Button Act: Next")
            changeDir()
        }

        button2.setOnAction {
            println("Button Act: Prev")
            if(curPath.compareTo(homePath) != 0) {
                backDir()
            } else {

            }
        }

        button1.setOnAction {
            println("Button Act: Home")
            while(curPath.compareTo(homePath) != 0) {
                backDir()
            }
        }

        menuShowHiddenFiles.setOnAction {
            println("switch observerbility of hidden files")
            val FILE = File(curPath)
            if(FILE.isDirectory()) {
                if(curPath[curPath.length-1] != '/' && curPath[curPath.length-1] != '\\') {
                    curPath = curPath + "/"
                }

                val list = ListView<String>()
                list.prefWidth = 225.0
                list.prefHeight = 450.0
                var templst = mutableListOf("")
                templst.removeLast()
                var tempFileList: MutableList<String>
                hiddenflag = !hiddenflag
                if(hiddenflag == false) {
                    tempFileList = changelst(curPath, templst)
                } else {
                    tempFileList = getFullLst(curPath, templst)
                }
                selectedDir = ""

                list.items = FXCollections.observableArrayList(tempFileList)
                //list.selectionModel.selectIndices(0)
                list.selectionModel.selectedItemProperty().addListener { _, oldValue, newValue ->
                    println("$oldValue --> $newValue")
                    val str = "$newValue"
                    selectedDir = curPath + str
                    println("New selected: $selectedDir")
                    val len = str.length
                    val FILE = File(selectedDir)
                    if((str[len-4] == '.' && str[len-3] == 't' && str[len-2] == 'x' && str[len-1] == 't')
                        || (str[len-3] == '.' && str[len-2] == 'm' && str[len-1] == 'd')) {
                        // if the selected one is text file
                        try {
                            val f = Paths.get(selectedDir)
                            val content = Files.readString(f)
                            val txt = Label(content)
                            val centerPane = ScrollPane(txt)
                            border.center = centerPane
                        } catch(e: Exception) {
                            val templabel = Label("Cannot read File")
                            border.center = templabel
                        }
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else if((str[len-4] == '.' && str[len-3] == 'p' && str[len-2] == 'n' && str[len-1] == 'g')
                        || (str[len-4] == '.' && str[len-3] == 'j' && str[len-2] == 'p' && str[len-1] == 'g')
                        || (str[len-4] == '.' && str[len-3] == 'b' && str[len-2] == 'm' && str[len-1] == 'p')) {
                        // if the selected one is an image
                        try {
                            val image = ImageView(Image(selectedDir))
                            val factor = min((575/(Image(selectedDir).width)),(450/(Image(selectedDir).height)))
                            image.setFitWidth(Image(selectedDir).width * factor)
                            image.setFitHeight(Image(selectedDir).height * factor)
                            border.center = image
                        } catch(e: Exception) {
                            val templabel = Label("Cannot read File")
                            border.center = templabel
                        }

                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else if(FILE.isDirectory()){
                        val blankPane = Pane()
                        blankPane.style = "-fx-background-colour: white"
                        border.center = blankPane
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else {
                        val tempBorder = BorderPane()
                        val messages = Label("Unsupported type")
                        tempBorder.center = messages
                        border.center = tempBorder
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    }

                }
                border.left = list
                Statuslabel = Label()
                status = Pane(Statuslabel)
                status.prefHeight = 18.0
                status.prefWidth = 800.0
                status.style = "-fx-background-colour: gray"
                border.bottom = status
                val blankPane = Pane()
                blankPane.style = "-fx-background-colour: white"
                border.center = blankPane
            }
        }

        val sceneHandler =
            EventHandler<KeyEvent> {event ->
                if(event.getCode() == KeyCode.ENTER) {
                    println("Enter pressed")
                    changeDir()
                } else if(event.getCode() == KeyCode.BACK_SPACE) {
                    println("Back space pressed")
                    backDir()
                } else if(event.getCode() == KeyCode.DELETE) {
                    println("Delete pressed")
                    backDir()
                }
            }

        scene.addEventFilter(KeyEvent.KEY_PRESSED, sceneHandler)

        fun refresh() {
            val FILE = File(curPath)
            if(FILE.isDirectory()) {
                if(curPath[curPath.length-1] != '/' && curPath[curPath.length-1] != '\\') {
                    curPath = curPath + "/"
                }

                val list = ListView<String>()
                list.prefWidth = 225.0
                list.prefHeight = 450.0
                var templst = mutableListOf("")
                templst.removeLast()
                var tempFileList: MutableList<String>
                if(hiddenflag == false) {
                    tempFileList = changelst(curPath, templst)
                } else {
                    tempFileList = getFullLst(curPath, templst)
                }
                selectedDir = ""

                list.items = FXCollections.observableArrayList(tempFileList)
                //list.selectionModel.selectIndices(0)
                list.selectionModel.selectedItemProperty().addListener { _, oldValue, newValue ->
                    println("$oldValue --> $newValue")
                    val str = "$newValue"
                    selectedDir = curPath + str
                    println("New selected: $selectedDir")
                    val len = str.length
                    val FILE = File(selectedDir)
                    if((str[len-4] == '.' && str[len-3] == 't' && str[len-2] == 'x' && str[len-1] == 't')
                        || (str[len-3] == '.' && str[len-2] == 'm' && str[len-1] == 'd')) {
                        // if the selected one is text file
                        try {
                            val f = Paths.get(selectedDir)
                            val content = Files.readString(f)
                            val txt = Label(content)
                            val centerPane = ScrollPane(txt)
                            border.center = centerPane
                        } catch(e: Exception) {
                            val templabel = Label("Cannot read File")
                            border.center = templabel
                        }
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else if((str[len-4] == '.' && str[len-3] == 'p' && str[len-2] == 'n' && str[len-1] == 'g')
                        || (str[len-4] == '.' && str[len-3] == 'j' && str[len-2] == 'p' && str[len-1] == 'g')
                        || (str[len-4] == '.' && str[len-3] == 'b' && str[len-2] == 'm' && str[len-1] == 'p')) {
                        // if the selected one is an image
                        try {
                            val image = ImageView(Image(selectedDir))
                            val factor = min((575/(Image(selectedDir).width)),(450/(Image(selectedDir).height)))
                            image.setFitWidth(Image(selectedDir).width * factor)
                            image.setFitHeight(Image(selectedDir).height * factor)
                            border.center = image
                        } catch(e: Exception) {
                            val templabel = Label("Cannot read File")
                            border.center = templabel
                        }

                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else if(FILE.isDirectory()){
                        val blankPane = Pane()
                        blankPane.style = "-fx-background-colour: white"
                        border.center = blankPane
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    } else {
                        val tempBorder = BorderPane()
                        val messages = Label("Unsupported type")
                        tempBorder.center = messages
                        border.center = tempBorder
                        Statuslabel = Label(selectedDir)
                        status = Pane(Statuslabel)
                        status.prefHeight = 18.0
                        status.prefWidth = 800.0
                        status.style = "-fx-background-colour: gray"
                        border.bottom = status
                    }

                }
                border.left = list
                Statuslabel = Label()
                status = Pane(Statuslabel)
                status.prefHeight = 18.0
                status.prefWidth = 800.0
                status.style = "-fx-background-colour: gray"
                border.bottom = status
                val blankPane = Pane()
                blankPane.style = "-fx-background-colour: white"
                border.center = blankPane
            }
        }
        // val temp_file = File(selectedDir)
        val warn = TextArea("Are you sure you want to DELETE this file/folder?")
        warn.isWrapText = true
        warn.prefWidth = 280.0
        warn.prefHeight = 125.0
        warn.relocate(10.0, 10.0)
        warn.isEditable = false
        warn.background = Background.EMPTY

        for(n in warn.childrenUnmodifiable) {
            warn.background = Background.EMPTY
        }

        fun recursiveDelete(f: File) {
            if (f.isDirectory()) {
                for (child in f.listFiles()) {
                    recursiveDelete(child)
                }
            }
            f.delete();
        }

        val yes = Button("Yes")
        yes.prefWidth = 75.0
        yes.relocate(130.0, 155.0)
        yes.setOnAction {
            println("yes selected")
            val FILE = File(selectedDir)
            recursiveDelete(FILE)
            refresh()
            stage.scene = scene
            stage.title = "File Browser"
            stage.show()
        }

        val no = Button("No")
        no.prefWidth = 75.0
        no.relocate(215.0, 155.0)
        no.setOnAction {
            println("no selected")
            stage.scene = scene
            stage.title = "File Browser"
            stage.show()
        }

        button4.setOnAction {
            println("Button Act: Delete")
            val scene2 = Scene(Pane(warn, yes, no), 300.0, 200.0)
            stage.scene = scene2
            stage.title = "WARNING"
            stage.isResizable = false
            stage.isAlwaysOnTop = true
            stage.show()
        }

        menuDelete.setOnAction {
            println("Menu Delete option selected")
            val scene2 = Scene(Pane(warn, yes, no), 300.0, 200.0)
            stage.scene = scene2
            stage.title = "WARNING"
            stage.isResizable = false
            stage.isAlwaysOnTop = true
            stage.show()
        }

        fun rename(newStr: String) {
            val oldFile = File(selectedDir)
            val newP = curPath + "/" + newStr
            val newFile = File(newP)
            if(newFile.exists()) {
                val warnn = TextArea("File/Folder with the same name already exists")
                flag_success = false
                warnn.isWrapText = true
                warnn.prefWidth = 280.0
                warnn.prefHeight = 125.0
                warnn.relocate(10.0, 10.0)
                warnn.isEditable = false
                warnn.background = Background.EMPTY
                for(n in warnn.childrenUnmodifiable) {
                    warnn.background = Background.EMPTY
                }

                val cancel = Button("cancel")
                cancel.prefWidth = 75.0
                cancel.relocate(215.0, 155.0)
                cancel.setOnAction {
                    println("cancel selected")
                    stage.scene = scene
                    stage.title = "File Browser"
                    stage.show()
                }
                val scene3 = Scene(Pane(warnn, cancel), 300.0, 200.0)
                stage.scene = scene3
                stage.title = "WARNING"
                stage.isResizable = false
                stage.isAlwaysOnTop = true
                stage.show()
            }
            // rename
            val successFlag = oldFile.renameTo(newFile)
            if(successFlag == false) {
                flag_success = false
                val warn = TextArea("Rename fails, may be change another name or check the permission of the file/directory")
                warn.isWrapText = true
                warn.prefWidth = 280.0
                warn.prefHeight = 125.0
                warn.relocate(10.0, 10.0)
                warn.isEditable = false
                warn.background = Background.EMPTY
                for(n in warn.childrenUnmodifiable) {
                    warn.background = Background.EMPTY
                }

                val cancel = Button("cancel")
                cancel.prefWidth = 75.0
                cancel.relocate(215.0, 155.0)
                cancel.setOnAction {
                    println("cancel selected")
                    stage.scene = scene
                    stage.title = "File Browser"
                    stage.show()
                }
                val scene4 = Scene(Pane(warn, cancel), 300.0, 200.0)
                stage.scene = scene4
                stage.title = "WARNING"
                stage.isResizable = false
                stage.isAlwaysOnTop = true
                stage.show()
            }
        }

        val tips = TextArea("Type in the new name for the file/folder")
        tips.isWrapText = true
        tips.prefWidth = 280.0
        tips.prefHeight = 30.0
        tips.relocate(10.0, 10.0)
        tips.isEditable = false
        tips.background = Background.EMPTY

        for(n in tips.childrenUnmodifiable) {
            tips.background = Background.EMPTY
        }

        val input = TextArea("")
        input.isWrapText = true
        input.prefWidth = 280.0
        input.prefHeight = 30.0
        input.relocate(10.0, 50.0)
        input.isEditable = true

        val confirm = Button("Confirm")
        confirm.prefWidth = 75.0
        confirm.relocate(130.0, 155.0)
        confirm.setOnAction {
            println("confirm selected")
            val name = input.getText()
            flag_success = true
            rename(name)
            if(flag_success) {
                refresh()
                stage.scene = scene
                stage.title = "File Browser"
                stage.show()
            }
        }

        val cancel2 = Button("Cancel")
        cancel2.prefWidth = 75.0
        cancel2.relocate(215.0, 155.0)
        cancel2.setOnAction {
            println("cancel selected")
            stage.scene = scene
            stage.title = "File Browser"
            stage.show()
        }

        button5.setOnAction {
            println("Button Act: Rename")
            val scene5 = Scene(Pane(tips, input, confirm, cancel2), 300.0, 200.0)
            stage.scene = scene5
            stage.title = "Changing name"
            stage.isResizable = false
            stage.isAlwaysOnTop = true
            stage.show()
        }

        menuRename.setOnAction {
            println("Menu Rename selected")
            val scene5 = Scene(Pane(tips, input, confirm, cancel2), 300.0, 200.0)
            stage.scene = scene5
            stage.title = "Changing name"
            stage.isResizable = false
            stage.isAlwaysOnTop = true
            stage.show()
        }



        fun moveF(loc: String) {
            val source = selectedDir
            var lenth = selectedDir.length
            if(selectedDir[lenth-1] == '/' || selectedDir[lenth-1] == '\\') {
                lenth--
            }
            var mark = 0
            for(i in 0..lenth-1) {
                if(selectedDir[i] == '/' || selectedDir[i] == '\\') {
                    mark = i
                }
            }
            var fileNamee = ""
            for(i in mark+1..lenth-1) {
                fileNamee = fileNamee + selectedDir[i]
            }
            val newDire = loc + "/" + fileNamee
            println(newDire)
            val oldFile = File(source)
            val newFile = File(newDire)
            if(newFile.exists()) {
                println("debug l912")
                val warnn = TextArea("File/Folder with the same name already exists")
                flag_success = false
                warnn.isWrapText = true
                warnn.prefWidth = 280.0
                warnn.prefHeight = 125.0
                warnn.relocate(10.0, 10.0)
                warnn.isEditable = false
                warnn.background = Background.EMPTY
                for(n in warnn.childrenUnmodifiable) {
                    warnn.background = Background.EMPTY
                }

                val cancel = Button("cancel")
                cancel.prefWidth = 75.0
                cancel.relocate(215.0, 155.0)
                cancel.setOnAction {
                    println("cancel selected")
                    stage.scene = scene
                    stage.title = "File Browser"
                    stage.show()
                }
                val scene3 = Scene(Pane(warnn, cancel), 300.0, 200.0)
                stage.scene = scene3
                stage.title = "WARNING"
                stage.isResizable = false
                stage.isAlwaysOnTop = true
                println("debug l939")
                stage.show()
            }
            // move
            if(flag_success) {
                val successFlag = oldFile.renameTo(newFile)
                if (successFlag == false) {
                    flag_success = false
                    val warn = TextArea("Move fails, may be check the permission")
                    warn.isWrapText = true
                    warn.prefWidth = 280.0
                    warn.prefHeight = 125.0
                    warn.relocate(10.0, 10.0)
                    warn.isEditable = false
                    warn.background = Background.EMPTY
                    for (n in warn.childrenUnmodifiable) {
                        warn.background = Background.EMPTY
                    }

                    val cancel = Button("cancel")
                    cancel.prefWidth = 75.0
                    cancel.relocate(215.0, 155.0)
                    cancel.setOnAction {
                        println("cancel selected")
                        stage.scene = scene
                        stage.title = "File Browser"
                        stage.show()
                    }
                    val scene4 = Scene(Pane(warn, cancel), 300.0, 200.0)
                    stage.scene = scene4
                    stage.title = "WARNING"
                    stage.isResizable = false
                    stage.isAlwaysOnTop = true
                    stage.show()
                }
            }
        }

        val tips2 = TextArea("Type in the new location for the file/folder\n(do not include the target name)")
        tips2.isWrapText = true
        tips2.prefWidth = 280.0
        tips2.prefHeight = 30.0
        tips2.relocate(10.0, 10.0)
        tips2.isEditable = false
        tips2.background = Background.EMPTY

        for(n in tips2.childrenUnmodifiable) {
            tips2.background = Background.EMPTY
        }

        val input2 = TextArea("")
        input2.isWrapText = true
        input2.prefWidth = 600.0
        input2.prefHeight = 30.0
        input2.relocate(10.0, 50.0)
        input2.isEditable = true

        val confirm2 = Button("Confirm")
        confirm2.prefWidth = 75.0
        confirm2.relocate(420.0, 155.0)
        confirm2.setOnAction {
            println("confirm selected")
            val loc = input2.getText()
            flag_success = true
            println("loc = $loc")
            moveF(loc)
            if(flag_success) {
                refresh()
                stage.scene = scene
                stage.title = "File Browser"
                stage.show()
            }
        }

        val cancel3 = Button("Cancel")
        cancel3.prefWidth = 75.0
        cancel3.relocate(515.0, 155.0)
        cancel3.setOnAction {
            println("cancel selected")
            stage.scene = scene
            stage.title = "File Browser"
            stage.show()
        }

        button6.setOnAction {
            println("Button Act: Move")
            val scene6 = Scene(Pane(tips2, input2, confirm2, cancel3), 650.0, 200.0)
            stage.scene = scene6
            stage.title = "Typing in the new location"
            stage.isResizable = false
            stage.isAlwaysOnTop = true
            stage.show()
        }

        menuMove.setOnAction {
            println("Menu Move selected")
            val scene6 = Scene(Pane(tips2, input2, confirm2, cancel3), 650.0, 200.0)
            stage.scene = scene6
            stage.title = "Typing in the new location"
            stage.isResizable = false
            stage.isAlwaysOnTop = true
            stage.show()
        }


    }
}