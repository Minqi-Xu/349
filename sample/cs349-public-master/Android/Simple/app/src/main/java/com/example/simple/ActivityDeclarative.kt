package com.example.simple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ActivityDeclarative : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // insert the xml declarative UI definition
        setContentView(R.layout.activity_declarative)
    }

    // onClick handler declared in xml UI definition
    fun myClickHandler(view: View) {
        println("** click **")
    }
}