package com.example.savestate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

    var counter = 0

    override fun onCreate(inState: Bundle?) {
        super.onCreate(inState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            startActivity(Intent(this, OtherActivity::class.java))
        }

        val buttonInc = findViewById<TextView>(R.id.buttonInc)
        val counterTextView = findViewById<TextView>(R.id.counter)
        buttonInc.setOnClickListener {
            counter++
            counterTextView.text = counter.toString()
        }

        // can also restore state here
//        if (inState != null) {
//           println("restoring state in onCreate")
//           with (inState) {
//                counter = getInt("COUNTER")
//            }
//        } else {
//            println("no saved state in onCreate")
//        }

        // initialize counter value dipslayed in UI
        counterTextView.text = counter.toString()
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

    override fun onPause() {
        super.onPause()
        println("onPause")
    }

    override fun onStop() {
        super.onStop()
        println("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {

        println("saving state in onSaveInstanceState")

        // save state
        with (outState) {
            putInt("COUNTER", counter)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(inState: Bundle) {
        super.onRestoreInstanceState(inState)

        println("restoring state in onRestoreInstanceState")

        val counterTextView = findViewById<TextView>(R.id.counter)

        with (inState) {
            counter = getInt("COUNTER")
            counterTextView.text = counter.toString()
        }
    }
}