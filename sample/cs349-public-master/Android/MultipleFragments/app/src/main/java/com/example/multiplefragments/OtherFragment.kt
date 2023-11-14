package com.example.multiplefragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.*
import androidx.navigation.fragment.findNavController


class OtherFragment : Fragment() {

//    args binding method:
//    import androidx.navigation.fragment.navArgs
//    private val args: OtherFragmentArgs by navArgs()
//    then access args using class, like args.message

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_other, container, false)

        // receive arguments here

        println(arguments)
        if (arguments != null && arguments!!.containsKey("message")) {
            val dataInTextField = root.findViewById<TextView>(R.id.dataIn)
            dataInTextField.text = arguments!!["message"].toString()
        }

        // return to main fragment
        val button = root.findViewById<Button>(R.id.button)
        button.setOnClickListener {

            val et = root.findViewById<EditText>(R.id.dataOut)
            val message = et.text.toString()

            // create a result
            if (message != "") {
                println("sending result with '$message'")

                // need to use the same result key and data key expected by MainFragment
                setFragmentResult("MyUniqueRequestKey",
                    bundleOf("MESSAGE" to message))
            }

            // navigate with a "return to previous Fragment" action (GOOD)
            findNavController().navigate(R.id.action_other_pop)
            // navigate to MainFragment as a new destination (BAD)
//            findNavController().navigate(R.id.action_otherFrag_to_mainFrag)
        }
        return root
    }
}