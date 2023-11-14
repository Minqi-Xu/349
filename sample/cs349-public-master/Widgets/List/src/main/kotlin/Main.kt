import javafx.application.Application
import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.control.ListView
import javafx.scene.layout.StackPane
import javafx.stage.Stage


class Main : Application() {
    override fun start(stage: Stage) {
        val list = ListView<String>()

        // You could hard-code the list data if it's immutable
        // list.items.addAll("One", "Two", "Three", "Four", "Five")

        // For mutable data, use a list collection
        list.items = FXCollections.observableArrayList("One", "Two", "Three", "Four", "Five")

        // Select the first item; you don't want an empty selection
        list.selectionModel.selectIndices(0)
        list.selectionModel.selectedItemProperty().addListener { _, oldValue, newValue ->
            println("$oldValue -> $newValue")
        }

        stage.title = "List Demo"
        stage.scene = Scene(StackPane(list), 400.0, 300.0)
        stage.isResizable = false
        stage.show()
    }
}