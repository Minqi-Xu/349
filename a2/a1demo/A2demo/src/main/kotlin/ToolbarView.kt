import javafx.scene.control.Button
import javafx.scene.control.ColorPicker
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import java.awt.TextArea

class ToolbarView(val model: Model) : VBox(), IView {
    val curdir = "${System.getProperty("user.dir")}/src/main/resources/"
    // Create Buttons
    val ButtonSelect = Button()
    val ButtonErase = Button()
    val ButtonLine = Button()
    val ButtonCircle = Button()
    val ButtonRect = Button()
    val ButtonFill = Button()

    val firstrow = HBox(ButtonSelect, ButtonErase)
    val secondrow = HBox(ButtonLine, ButtonCircle)
    val thirdrow = HBox(ButtonRect, ButtonFill)

    // Create the Tool panel
    val tools = VBox(firstrow, secondrow, thirdrow)

    // Create the colour filler
    val lineColor = ColorPicker()
    val fillColor = ColorPicker()
    val colors = HBox(lineColor, fillColor)

    // Create the line Thickness
    val path1 = curdir + "thick1.png"
    val thick1icon = ImageView(Image(path1))
    val thick1Button = Button()

    val path2 = curdir + "thick2.png"
    val thick2icon = ImageView(Image(path2))
    val thick2Button = Button()
    val path3 = curdir + "thick3.png"
    val thick3icon = ImageView(Image(path3))
    val thick3Button = Button()
    val path4 = curdir + "thick4.png"
    val thick4icon = ImageView(Image(path4))
    val thick4Button = Button()
    val thicks = HBox(thick1Button, thick2Button, thick3Button, thick4Button)

    // Create the Style
    val path5 = curdir + "style1.png"
    val s1 = ImageView(Image(path5))
    val s1B = Button()
    val path6 = curdir + "style2.png"
    val s2 = ImageView(Image(path6))
    val s2B = Button()
    val path7 = curdir + "style3.png"
    val s3 = ImageView(Image(path7))
    val s3B = Button()
    val path8 = curdir + "style4.png"
    val s4 = ImageView(Image(path8))
    val s4B = Button()
    val styles = HBox(s1B, s2B, s3B, s4B)

    init{
        val blank = Pane()
        blank.prefWidth = 150.0
        blank.prefHeight = 40.0
        val blank2 = Pane()
        blank2.prefWidth = 150.0
        blank2.prefHeight = 40.0
        val iv1 = ImageView(Image(curdir+"select.png"))
        ButtonSelect.setGraphic(iv1)
        ButtonSelect.prefWidth = 75.0
        ButtonSelect.prefHeight = 50.0
        val iv2 = ImageView(Image(curdir+"erase.png"))
        ButtonErase.setGraphic(iv2)
        ButtonErase.prefWidth = 75.0
        ButtonErase.prefHeight = 50.0
        val iv3 = ImageView(Image(curdir+"cir.png"))
        ButtonCircle.setGraphic(iv3)
        ButtonCircle.prefWidth = 75.0
        ButtonCircle.prefHeight = 50.0
        val iv4 = ImageView(Image(curdir+"fill.png"))
        ButtonFill.setGraphic(iv4)
        ButtonFill.prefWidth = 75.0
        ButtonFill.prefHeight = 50.0
        val iv5 = ImageView(Image(curdir+"line.png"))
        ButtonLine.setGraphic(iv5)
        ButtonLine.prefWidth = 75.0
        ButtonLine.prefHeight = 50.0
        val iv6 = ImageView(Image(curdir+"rect.png"))
        ButtonRect.setGraphic(iv6)
        ButtonRect.prefWidth = 75.0
        ButtonRect.prefHeight = 50.0
        lineColor.prefWidth = 75.0
        fillColor.prefWidth = 75.0
        thick1icon.setFitWidth(21.0)
        thick1icon.setFitHeight(21.0)
        thick1Button.setGraphic(thick1icon)
        thick1Button.prefWidth = 37.50
        thick1Button.prefHeight = 37.50
        thick2icon.setFitWidth(21.0)
        thick2icon.setFitHeight(21.0)
        thick2Button.setGraphic(thick2icon)
        thick2Button.prefWidth = 37.50
        thick2Button.prefHeight = 37.50
        thick3icon.setFitWidth(21.0)
        thick3icon.setFitHeight(21.00)
        thick3Button.setGraphic(thick3icon)
        thick3Button.prefWidth = 37.50
        thick3Button.prefHeight = 37.50
        thick4icon.setFitWidth(21.0)
        thick4icon.setFitHeight(21.0)
        thick4Button.setGraphic(thick4icon)
        thick4Button.prefWidth = 37.50
        thick4Button.prefHeight = 37.50
        s1.setFitWidth(21.0)
        s1.setFitHeight(21.0)
        s1B.setGraphic(s1)
        s1B.prefWidth = 37.50
        s1B.prefHeight = 37.50
        s2.setFitWidth(21.0)
        s2.setFitHeight(21.0)
        s2B.setGraphic(s2)
        s2B.prefWidth = 37.50
        s2B.prefHeight = 37.50
        s3.setFitWidth(21.0)
        s3.setFitHeight(21.0)
        s3B.setGraphic(s3)
        s3B.prefWidth = 37.50
        s3B.prefHeight = 37.50
        s4.setFitWidth(21.0)
        s4.setFitHeight(21.0)
        s4B.setGraphic(s4)
        s4B.prefWidth = 37.50
        s4B.prefHeight = 37.50
        this.children.add(tools)
        this.children.add(blank)
        this.children.add(colors)
        this.children.add(blank2)
        this.children.add(thicks)
        this.children.add(styles)
        ButtonSelect.setOnAction {
            println("Button Selected: Select")
            model.selectedTool = "Select"
            model.selecting = null
            model.notifyObservers()
        }
        ButtonErase.setOnAction {
            println("Button Selected: Erase")
            model.selectedTool = "Erase"
            model.selecting = null
            model.notifyObservers()
        }
        ButtonRect.setOnAction {
            println("Button Selected: Rect")
            model.selectedTool = "Rectangle"
            model.selecting = null
            model.notifyObservers()
        }
        ButtonLine.setOnAction {
            println("Button Selected: Line")
            model.selectedTool = "Line"
            model.selecting = null
            model.notifyObservers()
        }
        ButtonCircle.setOnAction {
            println("Button Selected: Circle")
            model.selectedTool = "Circle"
            model.selecting = null
            model.notifyObservers()
        }
        ButtonFill.setOnAction {
            println("Button Selected: Fill")
            model.selectedTool = "Fill"
            model.selecting = null
            model.notifyObservers()
        }
        lineColor.setOnAction {
            println("line color selecting")
            model.selectedLineColor = lineColor.value
            if(model.selecting != null) {
                model.selecting!!.lineColor = lineColor.value
                model.notifyObservers()
            }
            model.notifyObservers()
        }
        fillColor.setOnAction {
            println("fill color selecting")
            model.selectedFillColor = fillColor.value
            if(model.selecting != null) {
                model.selecting!!.fillColor = fillColor.value
                model.notifyObservers()
            }
            model.notifyObservers()
        }
        thick1Button.setOnMouseClicked {
            println("clicked on thick 1")
            model.selectedThick = 1
            if(model.selecting != null) {
                model.selecting!!.thick = 1
                model.notifyObservers()
            }
            model.notifyObservers()
        }
        thick2Button.setOnMouseClicked {
            println("clicked on thick 2")
            model.selectedThick = 2
            if(model.selecting != null) {
                model.selecting!!.thick = 2
                model.notifyObservers()
            }
            model.notifyObservers()
        }
        thick3Button.setOnMouseClicked {
            println("clicked on thick 3")
            model.selectedThick = 3
            if(model.selecting != null) {
                model.selecting!!.thick = 3
                model.notifyObservers()
            }
            model.notifyObservers()
        }
        thick4Button.setOnMouseClicked {
            println("clicked on thick 4")
            model.selectedThick = 4
            if(model.selecting != null) {
                model.selecting!!.thick = 4
                model.notifyObservers()
            }
            model.notifyObservers()
        }
        s1B.setOnMouseClicked {
            println("clicked on style 1")
            model.selectedStyle = 1
            if(model.selecting != null) {
                model.selecting!!.style = 1
                model.notifyObservers()
            }
            model.notifyObservers()
        }
        s2B.setOnMouseClicked {
            println("clicked on style 2")
            model.selectedStyle = 2
            if(model.selecting != null) {
                model.selecting!!.style = 2
                model.notifyObservers()
            }
            model.notifyObservers()
        }
        s3B.setOnMouseClicked {
            println("clicked on style 3")
            model.selectedStyle = 3
            if(model.selecting != null) {
                model.selecting!!.style = 3
                model.notifyObservers()
            }
            model.notifyObservers()
        }
        s4B.setOnMouseClicked {
            println("clicked on style 4")
            model.selectedStyle = 4
            if(model.selecting != null) {
                model.selecting!!.style = 4
                model.notifyObservers()
            }
            model.notifyObservers()
        }



    }



    override fun updateView() {
        lineColor.value = model.selectedLineColor
        fillColor.value = model.selectedFillColor

    }
}