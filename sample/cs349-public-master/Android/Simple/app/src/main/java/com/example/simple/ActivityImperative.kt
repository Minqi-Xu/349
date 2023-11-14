package com.example.simple

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ActivityImperative : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // root node is a simple layout
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        // label
        val label = TextView(this)
        label.text = "A message (imperative)"
        layout.addView(label)

        // button
        val button = Button(this)
        button.text = "Ok"
        layout.addView(button)

        button.setOnClickListener {
            println("** click **")
        }

        // add nodes to root of scene graph
        setContentView(layout)
    }
}