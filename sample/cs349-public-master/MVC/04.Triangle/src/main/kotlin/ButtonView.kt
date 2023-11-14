import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import java.text.NumberFormat

/*
* View a triangle as values:  numbers for the base, height, and hypotenuse.
* Manipulate with +/- buttons
*
* @author Original code by Byron Weber Becker
* @author Refactored into Kotlin and JavaFX by Jeff Avery
*/
class ButtonView(private val model: Model) : Pane(), IView {
    private val base = Label("0.0")
    private val height = Label("0.0")
    private val hypo = Label("0.0")
    private val baseDn = Button("-")
    private val baseUp = Button("+")
    private val heightDn = Button("-")
    private val heightUp = Button("+")
    private val formatter = NumberFormat.getNumberInstance()

    private fun layoutView() {
        val grid = GridPane()
        grid.add(Label("Base:"), 0, 0)
        grid.add(baseDn, 1, 0)
        grid.add(baseUp, 2, 0)
        grid.add(base, 3, 0)
        grid.add(Label("Height:"), 0, 1)
        grid.add(heightDn, 1, 1)
        grid.add(heightUp, 2, 1)
        grid.add(height, 3, 1)
        grid.add(Label("Hypotenuse:"), 0, 2)
        grid.add(hypo, 1, 2)
        grid.vgap = 5.0
        grid.hgap = 20.0
        grid.padding = Insets(15.0)
        children.add(grid)
    }

    private fun registerControllers() {
        baseUp.onAction = EventHandler {
            model.base = model.base + 1
        }
        baseDn.onAction = EventHandler {
            model.base = model.base - 1
        }
        heightUp.onAction = EventHandler {
            model.height = model.height + 1
        }
        heightDn.onAction = EventHandler {
            model.height = model.height - 1
        }
    }

    override fun updateView() {
        base.text = formatter.format(model.base)
        height.text = formatter.format(model.height)
        hypo.text = formatter.format(model.hypotenuse)

        // Updating the view includes enabling/disabling components!
        baseUp.isDisable = model.base > Model.MAX_SIDE
        baseDn.isDisable = model.base < 1
        heightUp.isDisable = model.height > Model.MAX_SIDE
        heightDn.isDisable = model.height < 1
    }

    init {
        layoutView()
        registerControllers()
        this.model.addView(this)
    }
}