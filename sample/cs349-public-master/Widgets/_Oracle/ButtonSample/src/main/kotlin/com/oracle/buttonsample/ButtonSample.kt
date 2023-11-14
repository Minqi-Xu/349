package com.oracle.buttonsample

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.effect.DropShadow
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage

class ButtonSample : Application() {
    private val color = Color.web("#464646")
    var button3 = Button("Decline")
    var shadow = DropShadow()
    var label = Label()

    override fun start(stage: Stage) {
        val scene = Scene(Group())
        stage.title = "Button Sample"
        stage.width = 300.0
        stage.height = 190.0

        label.font = Font.font("Times New Roman", 22.0)
        label.textFill = color
        val imageDecline = Image(javaClass.getResourceAsStream("not.png"))
        val imageAccept = Image(javaClass.getResourceAsStream("ok.png"))

        val vbox = VBox()
        vbox.layoutX = 20.0
        vbox.layoutY = 20.0
        val hbox1 = HBox()
        val hbox2 = HBox()

        val button1 = Button("Accept", ImageView(imageAccept))
        button1.style = "-fx-font: 22 arial; -fx-base: #b6e7c9;"
        button1.onAction = EventHandler { label.text = "Accepted" }
        val button2 = Button("Accept")
        button2.onAction = EventHandler { label.text = "Accepted" }
        button3.onAction = EventHandler { label.text = "Declined" }

        button3.addEventHandler(
            MouseEvent.MOUSE_ENTERED
        ) { button3.effect = shadow }

        button3.addEventHandler(
            MouseEvent.MOUSE_EXITED
        ) { button3.effect = null }

        hbox1.children.add(button2)
        hbox1.children.add(button3)
        hbox1.children.add(label)
        hbox1.spacing = 10.0
        hbox1.alignment = Pos.BOTTOM_CENTER

        val button4 = Button()
        button4.graphic = ImageView(imageAccept)
        button4.onAction = EventHandler { label.text = "Accepted" }

        val button5 = Button()
        button5.graphic = ImageView(imageDecline)
        button5.onAction = EventHandler { label.text = "Declined" }

        hbox2.children.add(button4)
        hbox2.children.add(button5)
        hbox2.spacing = 25.0

        vbox.children.add(button1)
        vbox.children.add(hbox1)
        vbox.children.add(hbox2)
        vbox.spacing = 10.0
        (scene.root as Group).children.add(vbox)

        stage.scene = scene
        stage.show()
    }
}