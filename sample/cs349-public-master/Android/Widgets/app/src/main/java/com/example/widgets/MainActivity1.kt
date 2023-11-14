package com.example.widgets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.widget.*

class MainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        // add edittext event handler
        val editText = findViewById<EditText>(R.id.editText)
        editText.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                println("editText `${editable.toString()}`")
            }
        })

        editText.setOnFocusChangeListener { view, focus ->
            if (!focus) {
                println("editText lost focus `${editText.text}`")
            }
        }

        // add radio group event handler
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.radioButton1 -> println("Radio 1")
                R.id.radioButton2 -> println("Radio 2")
                R.id.radioButton3 -> println("Radio 3")
            }
        }

        // add checkbox event handler
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        checkBox.setOnCheckedChangeListener { _, state ->
            println("Checkbox $state")
        }

        // add switch event handler
        val switch = findViewById<Switch>(R.id.switch1)
        switch.setOnCheckedChangeListener { _, state ->
            println("Switch $state")
        }

        // add button handler
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            println("Button clicked")
            // get values from the form
            println("${editText.text}, " +
                    "${radioGroup.checkedRadioButtonId}, " +
                    "${checkBox.isChecked}, " +
                    "${switch.isChecked}")
        }
    }

}