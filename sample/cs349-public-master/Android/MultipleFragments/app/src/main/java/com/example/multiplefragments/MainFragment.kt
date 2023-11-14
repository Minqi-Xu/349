package com.example.multiplefragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        // use simple intent with no data to start another activity
        val button = root.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFrag_to_otherFrag)
        }

        // include data with the intent to start another activity
        val buttonSendData = root.findViewById<Button>(R.id.buttonSendData)
        buttonSendData.setOnClickListener {
            val et = root.findViewById<EditText>(R.id.dataToSend)
            val message = et.text.toString()
            val action = MainFragmentDirections.actionMainFragToOtherFrag(message)
            findNavController().navigate(action)
        }

        // receive data from another fragment
        setFragmentResultListener("MyUniqueRequestKey") { requestKey, bundle ->
            println("received result in request key '$requestKey'")
            // get result in bundle
            val message = bundle.getString("MESSAGE")
            // show result in textview
            val et = root.findViewById<TextView>(R.id.dataReceived)
            et.text = message ?: ""
        }

        val buttonReceiveData = root.findViewById<Button>(R.id.buttonReceiveData)
        buttonReceiveData.setOnClickListener {
            // nothing special here, we just navigate
            findNavController().navigate(R.id.action_mainFrag_to_otherFrag)
        }

        return root
    }
}