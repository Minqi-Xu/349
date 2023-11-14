package com.example.multipleactivities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // use simple intent with no data to start another activity
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, OtherActivity::class.java)
            startActivity(intent)
        }

        // include data with the intent to start another activity
        val buttonSendData = findViewById<Button>(R.id.buttonSendData)
        buttonSendData.setOnClickListener {
            val et = findViewById<EditText>(R.id.dataToSend)
            val message = et.text.toString()
            val intent = Intent(this, OtherActivity::class.java).apply {
                putExtra("MESSAGE", message)
            }
            startActivity(intent)
        }

        // use an implicit intent to open an Activity in another application
        val buttonMaps = findViewById<Button>(R.id.buttonMaps)
        buttonMaps.setOnClickListener {
            // pass the text in the TextView
            val et = findViewById<EditText>(R.id.mapLocation)
            val location = et.text.toString()
            // "geo" is an implicit intent that will open a map activity
            val intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=$location")
            )
            startActivity(intent)
        }

        // receive a result from an activity
        // 1. create an activity launcher with a callback to receive data from the activity
        // StartActivityForResult is a generic "contract" to receive any kind of data as a result
        val startForResult = registerForActivityResult(StartActivityForResult()) { result ->
            println("startForResult")
            if (result.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = result.data
                val message = intent?.getStringExtra("MESSAGE")
                println("startForResult message: '$message'")
                if (message != null) {
                    val textView = findViewById<TextView>(R.id.dataReceived)
                    textView.text = message
                }
            }
        }
        // 2. use the activity launcher with the intent
        val buttonReceiveData = findViewById<Button>(R.id.buttonReceiveData)
        buttonReceiveData.setOnClickListener {
            println("start OtherActivity")
            val intent = Intent(this, OtherActivity::class.java)
            startForResult.launch(intent)
        }
    }
}