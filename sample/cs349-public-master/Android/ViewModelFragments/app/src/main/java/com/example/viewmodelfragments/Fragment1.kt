package com.example.viewmodelfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation

class Fragment1 : Fragment() {

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_1, container, false)

        val button = root.findViewById<Button>(R.id.button)

        button.setOnClickListener {
            viewModel.increment()
        }

        viewModel.counter.observe(this) {
            println("observe counter $it")
            button.text = it.toString()
        }

        return root
    }
}