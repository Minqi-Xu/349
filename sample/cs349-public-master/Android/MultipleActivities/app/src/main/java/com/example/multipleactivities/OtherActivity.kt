package com.example.multipleactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class OtherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)

        // return to main activity (with data if present)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            // add data to a result intent (only used by one of the demos)
            val et = findViewById<EditText>(R.id.dataOut)
            val message = et.text.toString()
            println("sending '$message'")
            // no intent details needed since calling finish returns to prev activity
            val intent = Intent().apply {
                putExtra("MESSAGE", message)
            }
            setResult(RESULT_OK, intent)
            // "finish" returns to activity that started this one
            finish()
        }

        // retrieve data from the starting intent
        val message = intent.getStringExtra("MESSAGE")
        if (message != null) {
            val textView = findViewById<TextView>(R.id.dataIn)
            textView.text = message
        }
    }
}