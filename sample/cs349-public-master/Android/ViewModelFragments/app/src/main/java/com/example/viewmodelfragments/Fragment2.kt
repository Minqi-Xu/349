package com.example.viewmodelfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation

class Fragment2 : Fragment() {

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_2, container, false)

        val textView = root.findViewById<TextView>(R.id.textView)

        viewModel.counter.observe(this) {
            println("fragment2 observe counter $it")
            textView.text = if (it > 0) "X".repeat(it) else " "
        }

        textView.setOnClickListener {
            viewModel.increment()
        }

        return root
    }
}