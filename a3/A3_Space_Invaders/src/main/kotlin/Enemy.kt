import javafx.scene.image.Image

class Enemy {
    var x = 0.0
    var y = 0.0
    val w = 50.0
    val h =40.0
    var moveLeft = true // if true, Periodically moving left
    var view: Image? = null

    fun set(X: Double, Y: Double) {
        x = X
        y = Y
    }

    fun changeDir() {
        moveLeft = !moveLeft
    }


}