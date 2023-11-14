package com.example.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.add


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // do fragment setup here
        println("MainActivity onCreate")

        // activity 1 is inserted declaratively in xml
        if (savedInstanceState == null) {

            // using the fragment manager built-in to the Activity
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<Fragment2>(R.id.fragment_container_bottom)
                addToBackStack(null)
            }
        }
    }
}