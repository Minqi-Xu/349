import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage

class Main : Application() {
    private val width:Double = 500.0
    private val height:Double = 500.0
    private var previousX = 0.0
    private var previousY = 0.0
    private var selectedSprite: Sprite? = null

    enum class OPERATION {TRANSLATE, SCALE_UP, SCALE_DOWN, ROTATE}
    var operation = OPERATION.TRANSLATE

    override fun start(stage: Stage) {
        // setup toggle buttons along the top
        val buttonGroup = ToggleGroup()
        val translateButton = ToggleButton("Translate")
        translateButton.onAction = EventHandler {
            operation = OPERATION.TRANSLATE
            println("TRANSLATE")
        }
        translateButton.toggleGroup = buttonGroup

        val scaleUpButton = ToggleButton("ScaleUp")
        scaleUpButton.onAction = EventHandler {
            operation = OPERATION.SCALE_UP
            println("SCALE UP")
        }
        scaleUpButton.toggleGroup = buttonGroup

        val scaleDownButton = ToggleButton("ScaleDown")
        scaleDownButton.onAction = EventHandler {
            operation = OPERATION.SCALE_DOWN
            println("SCALE DOWN")
        }
        scaleDownButton.toggleGroup = buttonGroup

        val rotateButton = ToggleButton("Rotate")
        rotateButton.onAction = EventHandler {
            operation = OPERATION.ROTATE
            println("ROTATE")
        }
        rotateButton.toggleGroup = buttonGroup

        // setup a canvas to use as a drawing surface
        val canvas = Canvas(width, height)
        val scene = Scene(VBox(HBox(translateButton, scaleDownButton, scaleUpButton, rotateButton), canvas), width, height)

        // create hierarchy of sprites
        val root: Sprite = createSprites()

        // add listeners
        // click selects the shape under the cursor
        // we have sprites do it since they track their own locations
        canvas.onMousePressed = EventHandler { mouseEvent: MouseEvent ->
            val hit: Sprite = root.getSpriteHit(mouseEvent.x, mouseEvent.y)!!
            selectedSprite = hit
            println("Selected " + selectedSprite.toString())
            previousX = mouseEvent.x
            previousY = mouseEvent.y
        }

        // un-selects any selected shape
        canvas.onMouseReleased = EventHandler {
            selectedSprite = null
            println("Unselected")
        }

        // dragged translates the shape based on change in mouse position
        // since shapes are defined relative to one another, they will follow their parent
        canvas.onMouseDragged = EventHandler { mouseEvent: MouseEvent ->
            if (selectedSprite != null) {
                when(operation) {
                    OPERATION.TRANSLATE -> {
                        // translate shape to follow the mouse cursor
                        val dx = mouseEvent.x - previousX
                        val dy = mouseEvent.y - previousY
                        selectedSprite!!.translate(dx, dy)
                    }
                    OPERATION.SCALE_DOWN -> {
                        selectedSprite!!.scale(0.99, 0.99)
                    }
                    OPERATION.SCALE_UP -> {
                        selectedSprite!!.scale(1.01, 1.01)
                    }
                    OPERATION.ROTATE -> {
                        val distance = Math.sqrt(
                            Math.pow(mouseEvent.x - previousX, 2.0) + Math.pow(mouseEvent.y - previousY, 2.0)
                        )
                        val theta = Math.atan(distance)
                        selectedSprite!!.rotate(theta)
                    }
                }

                // draw tree in new position
                draw(canvas, root)

                // save coordinates for next event
                previousX = mouseEvent.x
                previousY = mouseEvent.y
            }
        }

        // draw the sprites on the canvas
        draw(canvas, root)

        // show the scene including the canvas
        stage.scene = scene
        stage.show()
    }

    private fun draw(canvas: Canvas, root: Sprite) {
        val gc = canvas.graphicsContext2D
        gc.fill = Color.WHITE
        gc.fillRect(0.0, 0.0, canvas.width, canvas.height)
        root.draw(gc)
    }

    private fun createSprites(): Sprite {
        // create a bunch of different sprites at the origin
        val sprite1: Sprite = RectangleSprite(80.0, 50.0)
        val sprite2: Sprite = RectangleSprite(50.0, 40.0)
        val sprite3: Sprite = RectangleSprite(70.0, 30.0)

        // build scene graph aka tree from them
        sprite1.addChild(sprite2)
        sprite2.addChild(sprite3)

        // translate them to a starting position
        // this also places them beside one another
        sprite1.translate(10.0, 20.0)
        sprite2.translate(80.0, 5.0)
        sprite3.translate(50.0, 5.0)

        // return root of the tree
        return sprite1
    }
}