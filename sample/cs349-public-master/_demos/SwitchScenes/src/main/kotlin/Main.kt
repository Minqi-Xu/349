import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage

class Main : Application() {
    var scene1: Scene? = null
    var scene2: Scene? = null
    var button1: Button? = null
    var button2: Button? = null

    enum class SCENES {
        SCENE1, SCENE2
    }

    override fun start(stage: Stage?) {
        stage!!.title = "Switch Scenes"

        // scene one
        button1 = Button("Go To Scene2")
        val root1 = StackPane(button1)
        root1.background = Background(
            BackgroundFill(Color.DARKRED, CornerRadii.EMPTY, Insets.EMPTY))
        scene1 = Scene(root1, 300.0, 150.0)

        // pressing '2' or pressing the button will switch to scene 2
        EventHandler { event: KeyEvent ->
            if (event.code == KeyCode.DIGIT2) {
                setScene(stage, SCENES.SCENE2)
            }
        }.also { scene1?.onKeyPressed = it }
        button1?.onAction = EventHandler {
            setScene(
                stage, SCENES.SCENE2
            )
        }

        // scene two
        button2 = Button("Go To Scene1")
        val root2 = StackPane(button2)
        root2.background = Background(
            BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY))
        scene2 = Scene(root2, 300.0, 150.0)

        // pressing '1' or pressing the button will switch to scene 1
        scene2!!.onKeyPressed = EventHandler { event: KeyEvent ->
            if (event.code == KeyCode.DIGIT1) {
                setScene(stage, SCENES.SCENE1)
            }
        }
        button2!!.onAction = EventHandler {
            setScene(
                stage, SCENES.SCENE1
            )
        }

        // show starting scene
        setScene(stage, SCENES.SCENE1)
        stage.show()
    }

    private fun setScene(stage: Stage, scene: SCENES?) {
        when (scene) {
            SCENES.SCENE1 -> {
                stage.title = "Scene 1"
                stage.scene = scene1
            }
            SCENES.SCENE2 -> {
                stage.title = "Scene 2"
                stage.scene = scene2
            }
            else -> return
        }
    }
}