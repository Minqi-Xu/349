import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane

/*
* View a triangle as values:  numbers for the base, height, and hypotenuse.
* Manipulate with sliders. Hypotenuse reflects calculated value from model.
*
* @author Original code by Byron Weber Becker
* @author Refactored into Kotlin and JavaFX by Jeff Avery
*/
class SliderView(private val model: Model) : Pane(), IView {
    private val base = Slider()
    private val height = Slider()
    private val hypo = Slider()

    private fun layoutView() {
        val grid = GridPane()
        grid.add(Label("Base:"), 0, 0)
        grid.add(base, 1, 0)

        grid.add(Label("Height:"), 0, 1)
        grid.add(height, 1, 1)

        grid.add(Label("Hypotenuse:"), 0, 2)
        hypo.isDisable = true
        grid.add(hypo, 1, 2)

        grid.vgap = 5.0
        grid.hgap = 20.0
        grid.padding = Insets(15.0)
        children.add(grid)
    }

    private fun registerControllers() {
        base.onMouseDragged = EventHandler {
            model.base = base.value.toFloat()
        }
        height.onMouseDragged = EventHandler {
            model.height = height.value.toFloat()
        }
    }

    override fun updateView() {
        base.value = model.base.toDouble()
        height.value = model.height.toDouble()
        hypo.value = model.hypotenuse.toDouble()
    }

    init {
        layoutView()
        registerControllers()
        this.model.addView(this)
    }
}