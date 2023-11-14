import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.beans.value.ChangeListener


/*
* View a triangle as text:  numbers for the base, height, and hypotenuse.
* This view leaves a lot to be desired in terms of "polish".
* -- inconsistent decimal precision displayed, esp. in hypotenuse
* -- tabbing or clicking out of a text field doesn't update
* -- can edit the hypotenuse field
*
* @author Original code by Byron Weber Becker
* @author Refactored into Kotlin and JavaFX by Jeff Avery
*/
class TextView(private val model: Model) : Pane(), IView {
    private val baseTF = TextField()
    private val heightTF = TextField()
    private val hypoTF = Label()

    /**
     * What to do when the model changes.
     */
    override fun updateView() {
        baseTF.text = model.base.toString()
        heightTF.text = model.height.toString()
        // one way to format for a view, see ButtonView for another
        hypoTF.text = String.format("%.3f", model.hypotenuse)
    }

    private fun layoutView() {
        val grid = GridPane()
        grid.add(Label("Base:"), 0, 0)
        grid.add(baseTF, 1, 0)

        grid.add(Label("Height:"), 0, 1)
        grid.add(heightTF, 1, 1)

        grid.add(Label("Hypotenuse:"), 0, 2)
        grid.add(hypoTF, 1, 2)
        grid.vgap = 5.0
        grid.hgap = 20.0
        grid.padding = Insets(15.0)
        children.add(grid)
    }

    private fun registerControllers() {
        // Add a controller to interpret user actions in the base text field
        baseTF.onAction = EventHandler {
            val base = baseTF.text.toFloat()
            model.base = base
        }

        // bonus: method to handle focus events using a listener
        baseTF.focusedProperty().addListener { observable, oldValue, newValue ->
//            println("textfield $observable changed from $oldValue to $newValue")
            model.base = baseTF.text.toFloat()
        }

        // Add a controller to interpret user actions in the height text field
        heightTF.onAction = EventHandler {
            val height = heightTF.text.toFloat()
            model.height = height
        }

        // bonus1: method to handle focus events using a listener
        heightTF.focusedProperty().addListener { observable, oldValue, newValue ->
//            println("textfield $observable changed from $oldValue to $newValue")
            model.height = heightTF.text.toFloat()
        }

        // bonus2: method to handle all text changed events using a listener
        // (some implementation may not provide best behaviour  ...)
//        heightTF.textProperty().addListener { observable, oldValue, newValue ->
////            println("textfield $observable changed from $oldValue to $newValue")
//            model.height = heightTF.text.toFloat()
//        }

    }


    init {
        layoutView()
        registerControllers()

        // Add a this view as a listener to the model
        this.model.addView(this)
    }
}