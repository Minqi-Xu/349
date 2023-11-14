import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color

class Main : Application() {

    override fun start(stage: Stage) {

        val grid = GridPane()

        // setup grid
        grid.padding = Insets(10.0)
        grid.hgap = 10.0
        grid.vgap = 10.0
        grid.alignment = Pos.TOP_CENTER

        // add the form elements for the demo
        with (grid) {
            add(Label("Phone 1"), 0, 0)
            add(
                ValidatableTextField(
                    PhoneNumberValidation()
                ), 1, 0
            )
            add(Label("Phone 2"), 0, 1)
            add(
                ValidatableTextField(
                    PhoneNumberValidation(),
                    filter = false
                ), 1, 1
            )
            add(Label("Phone 3"), 0, 2)
            add(
                ValidatableTextField(
                    PhoneNumberValidation(),
                    filter = false,
                    validateKeystrokes = false
                ), 1, 2
            )
//            add(Label("Phone 4"), 0, 2)
            val vtf = ValidatableTextField(
                PhoneNumberValidation()
            )
            vtf.textField.promptText = "Phone 4"
            add(
                vtf, 1, 3
            )
            add(Label("Postal Code"), 0, 4)
            add(
                ValidatableTextField(
                    PostalCodeValidation(),
                ), 1, 4
            )
            add(Label("Number"), 0, 5)
            add(
                ValidatableTextField(
                    NumericValidation(),
                ), 1, 5
            )
        }

        // to keep labels aligned with the textfield
        for (c in grid.children) {
            GridPane.setValignment(c, VPos.BASELINE)
        }

        val scene = Scene(StackPane(grid), 400.0, 400.0)
        stage.title = "Input/Validation"
        stage.scene = scene
        stage.show()

//        startOnScreen2(stage)
    }
}