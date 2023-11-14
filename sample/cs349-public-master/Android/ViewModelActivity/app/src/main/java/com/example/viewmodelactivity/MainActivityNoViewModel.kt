package com.example.viewmodelactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
/*
    IMPORTANT! the androidx.activity.viewModels import
    requires two "dependencies" in the "Module" build.gradle
    (i.e. "app/build.gradle" in file structure)
    dependencies {
       implementation 'androidx.activity:activity-ktx:1.4.0'
       implementation "androidx.fragment:fragment-ktx:1.4.1"
       ...
    }
*/
// viewmodel imports
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateViewModelFactory

/*
Demonstrates why state is lost without ViewModel
DO NOT DO THIS!
*/
class MainActivityNoViewModel : AppCompatActivity() {

    // create view model using delegation
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // uses same layout
        setContentView(R.layout.activity_main)

        // change the ActionBar title so we can tell activities apart
        supportActionBar?.title = "MainActivity with No ViewModel"

        println("onCreate")

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        fun update(value: Int) {
            println("update counter $value")
            button.text = value.toString()
            textView.text = if (value > 0) "X".repeat(value) else " "
        }

        button.setOnClickListener {
            count++
            update(count)
        }

        textView.setOnClickListener {
            count++
            update(count)
        }

        // initial update
        update(count)
    }
}

