import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.effect.SepiaTone
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.stage.Stage

class SliderSample : Application() {
    val opacityLevel = Slider(0.0, 1.0, 1.0)
    val sepiaTone = Slider(0.0, 1.0, 1.0)
    val scaling = Slider(0.5, 1.0, 1.0)
    val image = Image(
        javaClass.getResourceAsStream(
            "cappuccino.jpg"
        )
    )
    val opacityCaption = Label("Opacity Level:")
    val sepiaCaption = Label("Sepia Tone:")
    val scalingCaption = Label("Scaling Factor:")
    val opacityValue = Label(
        java.lang.Double.toString(opacityLevel.value)
    )
    val sepiaValue = Label(
        java.lang.Double.toString(sepiaTone.value)
    )
    val scalingValue = Label(
        java.lang.Double.toString(scaling.value)
    )

    override fun start(stage: Stage) {
        val textColor = Color.BLACK
        val sepiaEffect = SepiaTone()

        val root = Group()
        val scene = Scene(root, 410.0, 400.0)
        stage.scene = scene
        stage.title = "Slider Sample"
        scene.fill = Color.BLACK

        val grid = GridPane()
        grid.padding = Insets(10.0, 10.0, 10.0, 10.0)
        grid.vgap = 10.0
        grid.hgap = 70.0

        val cappuccino = ImageView(image)
        cappuccino.effect = sepiaEffect

        GridPane.setConstraints(cappuccino, 0, 0)
        GridPane.setColumnSpan(cappuccino, 3)
        grid.children.add(cappuccino)
        scene.root = grid
        opacityCaption.textFill = textColor

        GridPane.setConstraints(opacityCaption, 0, 1)
        grid.children.add(opacityCaption)
        opacityLevel.valueProperty().addListener { ov, old_val, new_val ->
            cappuccino.opacity = new_val.toDouble()
            opacityValue.text = String.format("%.2f", new_val)
        }

        GridPane.setConstraints(opacityLevel, 1, 1)
        grid.children.add(opacityLevel)
        opacityValue.textFill = textColor

        GridPane.setConstraints(opacityValue, 2, 1)
        grid.children.add(opacityValue)
        sepiaCaption.textFill = textColor

        GridPane.setConstraints(sepiaCaption, 0, 2)
        grid.children.add(sepiaCaption)
        sepiaTone.valueProperty().addListener { ov, old_val, new_val ->
            sepiaEffect.level = new_val.toDouble()
            sepiaValue.text = String.format("%.2f", new_val)
        }

        GridPane.setConstraints(sepiaTone, 1, 2)
        grid.children.add(sepiaTone)
        sepiaValue.textFill = textColor

        GridPane.setConstraints(sepiaValue, 2, 2)
        grid.children.add(sepiaValue)
        scalingCaption.textFill = textColor

        GridPane.setConstraints(scalingCaption, 0, 3)
        grid.children.add(scalingCaption)
        scaling.valueProperty().addListener { ov, old_val, new_val ->
            cappuccino.scaleX = new_val.toDouble()
            cappuccino.scaleY = new_val.toDouble()
            scalingValue.text = String.format("%.2f", new_val)
        }

        GridPane.setConstraints(scaling, 1, 3)
        grid.children.add(scaling)
        scalingValue.textFill = textColor

        GridPane.setConstraints(scalingValue, 2, 3)
        grid.children.add(scalingValue)
        stage.isResizable = false
        stage.show()
    }
}