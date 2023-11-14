package com.oracle.progresssample

import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.control.ProgressIndicator
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class ProgressSample : Application() {
    val values = arrayOf(-1.0f, 0f, 0.6f, 1.0f)
    val labels = arrayOfNulls<Label>(values.size)
    val pbs = arrayOfNulls<ProgressBar>(values.size)
    val pins = arrayOfNulls<ProgressIndicator>(values.size)
    val hbs = arrayOfNulls<HBox>(values.size)

    override fun start(stage: Stage) {
        val root = Group()
        val scene = Scene(root, 300.0, 150.0)
        stage.scene = scene
        stage.title = "Progress Controls"

        for (i in values.indices) {
            labels[i] = Label()
            val label = labels[i]
            label!!.text = "progress:" + values[i]
            pbs[i] = ProgressBar()
            val pb = pbs[i]
            pb!!.progress = values[i].toDouble()
            pins[i] = ProgressIndicator()
            val pin = pins[i]
            pin!!.progress = values[i].toDouble()
            hbs[i] = HBox()
            val hb = hbs[i]
            hb!!.spacing = 5.0
            hb.alignment = Pos.CENTER
            hb.children.addAll(label, pb, pin)
        }
        val vb = VBox()
        vb.spacing = 5.0
        vb.children.addAll(*hbs)
        scene.root = vb
        stage.show()
    }
}