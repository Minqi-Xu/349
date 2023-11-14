package com.example.widgets

import android.os.Bundle
import android.widget.*
import android.text.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
// this is the generated binding from our layout "activity_main2.xml"
import com.example.widgets.databinding.ActivityMain2Binding


class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize the binding
        val B: ActivityMain2Binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        // add edittext event handler
        B.editText.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                println("editText `${editable.toString()}`")
            }
        })

        B.editText.setOnFocusChangeListener { view, focus ->
            if (!focus) {
                println("editText lost focus `${B.editText.text}`")
            }
        }

        // add radio group event handler
        B.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.radioButton1 -> println("Radio 1")
                R.id.radioButton2 -> println("Radio 2")
                R.id.radioButton3 -> println("Radio 3")
            }
        }

        // add checkbox event handler
        B.checkBox.setOnCheckedChangeListener { _, state ->
            println("Checkbox $state")
        }

        // add switch event handler
        B.switch1.setOnCheckedChangeListener { _, state ->
            println("Switch $state")
        }

        // add button handler
        B.button.setOnClickListener {
            println("Button clicked")
            // get values from the form
            println("${B.editText.text}, " +
                    "${B.radioGroup.checkedRadioButtonId}, " +
                    "${B.checkBox.isChecked}, " +
                    "${B.switch1.isChecked}")
        }
    }

    override fun onStart() {
        super.onStart()

        println("** using layout binding **")
    }

}