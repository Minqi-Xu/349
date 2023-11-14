package com.example.simple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityMix : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // insert the xml declarative UI definition
        setContentView(R.layout.activity_mix)

        // retrieve the button node from declarative ui definition
        val button = findViewById<Button>(R.id.okbutton)
        // add an event handler
        button.setOnClickListener {
            println("** click **")
//            println(R.string.click_message)
        }
    }
}