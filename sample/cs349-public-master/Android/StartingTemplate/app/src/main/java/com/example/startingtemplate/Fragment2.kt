package com.example.startingtemplate

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

class Fragment2 : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // layout is defined in "res/layouts/fragment_2.xml"
        val root = inflater.inflate(R.layout.fragment_2, container, false)

        // add UI handlers that call your viewmodel here

        // observe viewModel properties here

        viewModel.property.observe(this) {
            println("observe property $it")
            // update UI here

        }

        return root
    }
}