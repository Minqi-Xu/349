package com.oracle.choiceboxsample

import javafx.application.Application
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.stage.Stage

class ChoiceboxSample : Application() {
    var rect = Rectangle(150.0, 30.0)
    val label = Label("Hello")
    override fun start(stage: Stage) {
        val scene = Scene(Group())
        scene.fill = Color.ALICEBLUE
        stage.scene = scene
        stage.show()
        stage.title = "ChoiceBox Sample"
        stage.width = 300.0
        stage.height = 200.0
        label.font = Font.font("Arial", 25.0)
        label.layoutX = 40.0
        val greetings = arrayOf(
            "Hello", "Hola", "Привет", "你好",
            "こんにちは"
        )
        val cb: ChoiceBox<*> = ChoiceBox<String>(
            FXCollections.observableArrayList<String>(
                "English", "Español", "Русский", "简体中文", "日本語"
            )
        )
        cb.selectionModel.selectedIndexProperty().addListener(object : ChangeListener<Number> {
            override fun changed(observable: ObservableValue<out Number>?, oldValue: Number?, newValue: Number?) {
                if (newValue != null) {
                    label.text = greetings[newValue.toInt()]
                }
            }
        })
        cb.tooltip = Tooltip("Select the language")
        cb.value = "English"
        val hb = HBox()
        hb.children.addAll(cb, label)
        hb.spacing = 30.0
        hb.alignment = Pos.CENTER
        hb.padding = Insets(10.0, 0.0, 0.0, 10.0)
        (scene.root as Group).children.add(hb)
    }
}