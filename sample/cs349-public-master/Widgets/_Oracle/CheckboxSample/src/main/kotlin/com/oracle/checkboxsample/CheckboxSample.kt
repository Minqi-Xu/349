package com.oracle.checkboxsample

import javafx.application.Application
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.CheckBox
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage

class CheckboxSample : Application() {
    var rect = Rectangle(90.0, 30.0)
    val names = arrayOf("Security", "Project", "Chart")
    val images = arrayOfNulls<Image>(names.size)
    val icons = arrayOfNulls<ImageView>(names.size)
    val cbs = arrayOfNulls<CheckBox>(names.size)

    override fun start(stage: Stage) {
        val scene = Scene(Group())
        stage.title = "Checkbox Sample"
        stage.width = 230.0
        stage.height = 120.0
        rect.arcHeight = 10.0
        rect.arcWidth = 10.0
        rect.fill = Color.rgb(41, 41, 41)

        for (i in names.indices) {
            images[i] = Image(javaClass.getResourceAsStream(names[i] + ".png"))
            val image = images[i]
            icons[i] = ImageView()
            val icon = icons[i]
            cbs[i] = CheckBox(names[i])
            val cb = cbs[i]
            cb!!.selectedProperty().addListener(object : ChangeListener<Boolean> {
                override fun changed(
                    observable: ObservableValue<out Boolean>?,
                    oldValue: Boolean?,
                    newValue: Boolean?
                ) {
                    icon!!.image = if (newValue != null && newValue) image else null
                }
            })
        }
        val vbox = VBox()
        vbox.children.addAll(*cbs)
        vbox.spacing = 5.0

        val hbox = HBox()
        hbox.children.addAll(*icons)
        hbox.padding = Insets(0.0, 0.0, 0.0, 5.0)

        val stack = StackPane()
        stack.children.add(rect)
        stack.children.add(hbox)
        StackPane.setAlignment(rect, Pos.TOP_CENTER)

        val root = HBox()
        root.children.add(vbox)
        root.children.add(stack)
        root.spacing = 40.0
        root.padding = Insets(20.0, 10.0, 10.0, 20.0)
        (scene.root as Group).children.add(root)

        stage.scene = scene
        stage.show()
    }
}