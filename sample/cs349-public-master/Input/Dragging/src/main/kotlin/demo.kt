import javafx.stage.Screen
import javafx.stage.Stage

fun startOnScreen2(stage: Stage) {
    // keep application window on secondary display
    val w = Screen.getScreens()[1].getVisualBounds().maxX;
    stage.x = w - stage.scene.width - 32.0
    stage.y = 64.0
}