package com.example.savestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OtherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)

        val b = findViewById<Button>(R.id.buttonBack)
        b.setOnClickListener {
            finish()
        }
    }
}