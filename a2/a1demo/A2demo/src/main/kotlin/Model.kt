import javafx.scene.paint.Color
import java.awt.Shape

class Model {
    private val views: ArrayList<IView> = ArrayList()

    fun addView(view: IView) {
        views.add(view)
        view.updateView()
    }

    fun notifyObservers() {
        for(view in views) {
            println("Model: Notify View")
            view.updateView()
        }
    }


    var selectedTool = "Select"
    var selectedLineColor = Color.BLACK
    var selectedFillColor = Color.WHITE
    var selectedThick = 1
    var selectedStyle = 1
    var shapes = ArrayList<MyShape>()
    var selecting: MyShape? = null




}