import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*

import javafx.scene.text.Font

class View(
   val model: AbstractModel
   ): VBox(), IView {

   val listView = ListView<Int>()

   var progressBar = ProgressBar(0.0).apply {
      // progress bars seem to default to 0 height
      maxWidth = Double.MAX_VALUE
      minHeight = 20.0
   }

   init {
      // make layout whitespce nice
      padding = Insets(16.0)
      spacing = 16.0

      val name = model::class.simpleName.toString()
      val title = Label(name).apply {
         font = Font(18.0)
      }

      val stop = Button("Cancel").apply {
         minWidth = 100.0
      }

      stop.setOnMouseClicked {
         model.cancel()
      }

      val start = Button("Start").apply {
         isDefaultButton = true
         minWidth = 100.0
      }

      start.setOnMouseClicked {
         start.isDisable = true
         model.start()
         start.isDisable = false
      }

      val buttons = HBox(stop, start).apply {
         spacing = 16.0
         alignment = Pos.CENTER_RIGHT
      }

      this.children.addAll(title, listView, progressBar, buttons)

      model.addView(this)
   }

   override fun update() {
      println(model.progress)
      progressBar.progress = model.progress
      // clear out list and add it back in
      listView.items.clear()
      listView.items.addAll(model.primes)
   }
}