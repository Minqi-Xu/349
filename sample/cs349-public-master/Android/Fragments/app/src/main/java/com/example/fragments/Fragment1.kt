package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
/*
    IMPORTANT! the androidx.fragment.app.* import
    requires two "dependencies" in the "Module" build.gradle
    (i.e. "app/build.gradle" in file structure)
    dependencies {
    implementation "androidx.fragment:fragment:1.4.1"
    implementation "androidx.fragment:fragment-ktx:1.4.1"
       ...
    }
*/
import androidx.fragment.app.Fragment

class Fragment1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // do fragment setup here
        println("Fragment1 onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1,
            container, false)
    }
}