package com.example.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // switch activity to view different demos
        val layout = when (3) {
            // finished layouts that match lecture slides
            1 -> R.layout.linear_layout
            2 -> R.layout.constraint_layout
            3 -> R.layout.flow_layout
            // unconstrained layout for demoing how to add constraints
            else -> R.layout.demo_layout
        }

        setContentView(layout)

    }
}