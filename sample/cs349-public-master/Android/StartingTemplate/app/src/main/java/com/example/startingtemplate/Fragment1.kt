package com.example.startingtemplate

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

class Fragment1 : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // layout is defined in "res/layouts/fragment_1.xml"
        val root = inflater.inflate(R.layout.fragment_1, container, false)

        // add UI handles for navigation here
        val button1 = root.findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            // (setup navigation actions in "rs/navigation/navigation.xml")
            findNavController().navigate(R.id.action_f1_to_f2)
        }

        // add UI handlers that call your viewmodel here

        // observe viewModel properties here

        viewModel.property.observe(this) {
            println("observe property $it")
            // update UI here

        }

        return root
    }
}