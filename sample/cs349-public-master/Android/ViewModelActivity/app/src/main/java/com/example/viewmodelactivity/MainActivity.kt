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

class MainActivity : AppCompatActivity() {

    // create view model using delegation
    private val viewModel: MainActivityViewModel1 by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("onCreate")

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        viewModel.counter.observe(this) {
            println("observe counter $it")
            button.text = it.toString()
            textView.text = if (it > 0) "X".repeat(it) else " "
        }

        button.setOnClickListener {
            viewModel.increment()
        }

        textView.setOnClickListener {
            viewModel.increment()
        }
    }
}

